package pers.wayease.duolaimall.search.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.search.converter.GoodsConverter;
import pers.wayease.duolaimall.search.pojo.dto.SearchAttributeDto;
import pers.wayease.duolaimall.search.pojo.dto.SearchPageDto;
import pers.wayease.duolaimall.search.pojo.dto.SearchTrademarkDto;
import pers.wayease.duolaimall.search.pojo.model.Goods;
import pers.wayease.duolaimall.search.pojo.param.SearchParam;
import pers.wayease.duolaimall.search.service.SearchService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.service.impl
 * @name SearchServiceImpl
 * @description Search service implement class.
 * @since 2024-10-10 14:23
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private GoodsConverter goodsConverter;

    @Override
    public SearchPageDto search(SearchParam searchParam) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        buildRequest(nativeSearchQueryBuilder, searchParam);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        SearchHits searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class);
        SearchPageDto searchPageDto = parseQuery(searchHits, searchParam);
        return searchPageDto;
    }

    private void buildRequest(NativeSearchQueryBuilder nativeSearchQueryBuilder, SearchParam searchParam) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // keyword condition
        if (StringUtils.isNotBlank(searchParam.getKeyword())) {
            MatchQueryBuilder titleMatchQueryBuilder = QueryBuilders.matchQuery("title", searchParam.getKeyword());
            boolQueryBuilder.must(titleMatchQueryBuilder);
        }

        // category condition
        if (searchParam.getThirdLevelCategoryId() != null) {
            TermQueryBuilder thirdLevelCategoryIdTermQueryBuilder = QueryBuilders.termQuery("thirdLevelCategoryId", searchParam.getThirdLevelCategoryId());
            boolQueryBuilder.filter(thirdLevelCategoryIdTermQueryBuilder);
        }

        // trademark condition
        if (StringUtils.isNotBlank(searchParam.getTrademark())) {
            String[] trademarkSplitArray = searchParam.getTrademark().split(":");
            TermQueryBuilder trademarkTermQueryBuilder = QueryBuilders.termQuery("tmId", trademarkSplitArray[0]);
            boolQueryBuilder.filter(trademarkTermQueryBuilder);
        }

        // attribute condition
        String[] propArray = searchParam.getProps();
        if (propArray != null) {
            for (String prop : propArray) {
                String[] propSplitArray = prop.split(":");
                BoolQueryBuilder attributeBoolQueryBuilder = QueryBuilders.boolQuery();
                TermQueryBuilder attributeIdTermQueryBuilder = QueryBuilders.termQuery("attrs.attrId", propSplitArray[0]);
                TermQueryBuilder attributeValueTermQueryBuilder = QueryBuilders.termQuery("attrs.attrValue", propSplitArray[1]);
                attributeBoolQueryBuilder.filter(attributeIdTermQueryBuilder);
                attributeBoolQueryBuilder.filter(attributeValueTermQueryBuilder);
                NestedQueryBuilder attributeNestedQueryBuilder = QueryBuilders.nestedQuery("attrs", attributeBoolQueryBuilder, ScoreMode.None);
                boolQueryBuilder.filter(attributeNestedQueryBuilder);
            }
        }

        // query
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        // page
        // -1 because the ruled first page is 0
        PageRequest pageRequest = PageRequest.of(searchParam.getPageNo() - 1, searchParam.getPageSize());
        nativeSearchQueryBuilder.withPageable(pageRequest);

        // sort
        if (StringUtils.isNotBlank(searchParam.getOrder())) {
            String[] sortSplitArray = searchParam.getOrder().split(":");

            String sortName = !("2".equals(sortSplitArray[0]))
                    // if 1 or default
                    ? "hotScore"
                    // if 2
                    : "price";

            Sort.Direction sortDirection = "desc".equals(StringUtils.lowerCase(sortSplitArray[1]))
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            Sort sort = Sort.by(sortDirection, sortName);
            nativeSearchQueryBuilder.withSort(sort);
        }

        // highlight
        if (StringUtils.isNotBlank(searchParam.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title")
                    .preTags("<span style='color: red'>")
                    .postTags("</span>");
            nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);
        }

        // tmId aggregation
        TermsAggregationBuilder tmIdTermsAggregationBuilder = AggregationBuilders.terms("tmId_agg").field("tmId")
                .subAggregation(
                        AggregationBuilders.terms("tmName_agg").field("tmName")
                )
                .subAggregation(
                        AggregationBuilders.terms("tmUrl_agg").field("tmLogoUrl")
                );
        nativeSearchQueryBuilder.withAggregations(tmIdTermsAggregationBuilder);

        // attribute aggregation
        NestedAggregationBuilder attributeNestedAggregationBuilder = AggregationBuilders.nested("attrAgg", "attrs")
                .subAggregation(
                        AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")
                                .subAggregation(
                                        AggregationBuilders.terms("attrNameAgg").field("attrs.attrName")
                                )
                                .subAggregation(
                                        AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue")
                                )
                );
        nativeSearchQueryBuilder.withAggregations(attributeNestedAggregationBuilder);
    }

    private SearchPageDto parseQuery(SearchHits<Goods> goodsSearchHits, SearchParam searchParam) {
        SearchPageDto searchPageDto = new SearchPageDto();

        // goods list
        List<SearchHit<Goods>> goodsSearchHitList = goodsSearchHits.getSearchHits();
        List<Goods> goodsList = goodsSearchHitList.stream()
                .map(goodsSearchHit -> {
                    Goods goods = goodsSearchHit.getContent();
                    if (!goodsSearchHit.getHighlightFields().isEmpty()) {
                        List<String> titleList = goodsSearchHit.getHighlightField("title");
                        goods.setTitle(titleList.get(0));
                    }
                    return goods;
                })
                .toList();
        searchPageDto.setGoodsList(goodsConverter.goodsPoList2DtoList(goodsList));

        Aggregations aggregations = (Aggregations) goodsSearchHits.getAggregations().aggregations();

        // trademark list
        Terms tmIdAgg = aggregations.get("tmId_agg");
        List<SearchTrademarkDto> tmDtoList = tmIdAgg.getBuckets().stream()
                .map(bucket -> {
                    SearchTrademarkDto searchTrademarkDto = new SearchTrademarkDto();
                    searchTrademarkDto.setTmId((Long)bucket.getKey());
                    Aggregations bucketAggregations = bucket.getAggregations();
                    Terms tmUrlAgg = bucketAggregations.get("tmUrl_agg");
                    searchTrademarkDto.setTmLogoUrl(tmUrlAgg.getBuckets().get(0).getKeyAsString());
                    Terms tmNameAgg = bucketAggregations.get("tmName_agg");
                    searchTrademarkDto.setTmName(tmNameAgg.getBuckets().get(0).getKeyAsString());
                    return searchTrademarkDto;
                })
                .toList();
        searchPageDto.setTrademarkList(tmDtoList);

        // attribute list
        Nested attrAgg = aggregations.get("attrAgg");
        Aggregations attributeAggregations = attrAgg.getAggregations();
        Terms attrIdAgg = attributeAggregations.get("attrIdAgg");
        List<SearchAttributeDto> searchAttributeDtoList = attrIdAgg.getBuckets().stream()
                .map(bucket -> {
                    SearchAttributeDto searchAttributeDto = new SearchAttributeDto();
                    searchAttributeDto.setAttrId((Long)bucket.getKey());
                    Aggregations bucketAggregations = bucket.getAggregations();
                    Terms attrNameAgg = bucketAggregations.get("attrNameAgg");
                    searchAttributeDto.setAttrName((attrNameAgg.getBuckets().get(0).getKeyAsString()));
                    Terms attrValueAgg = bucketAggregations.get("attrValueAgg");
                    List<String> attrValueList = attrValueAgg.getBuckets().stream()
                            .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                            .toList();
                    searchAttributeDto.setAttrValueList(attrValueList);
                    return searchAttributeDto;
                })
                .toList();
        searchPageDto.setAttrsList(searchAttributeDtoList);

        // page
        long totalHits = goodsSearchHits.getTotalHits();
        Integer pageSize = searchParam.getPageSize();
        searchPageDto.setTotal(totalHits);
        searchPageDto.setPageSize(pageSize);
        searchPageDto.setPageNo(searchParam.getPageNo());
        long totalPage = (long) Math.ceil((double)totalHits / (double)pageSize);
        searchPageDto.setTotalPages(totalPage);
        return searchPageDto;
    }
}
