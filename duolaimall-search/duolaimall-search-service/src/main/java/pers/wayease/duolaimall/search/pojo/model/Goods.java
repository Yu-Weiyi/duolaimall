package pers.wayease.duolaimall.search.pojo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.pojo.model
 * @name Goods
 * @description Goods class.
 * @since 2024-10-10 16:14
 */
@Document(indexName = "goods", shards = 1, replicas = 0)
@Data
public class Goods {

    @Id
    private Long id;
    @Field(type = FieldType.Keyword, index = false)
    private String defaultImg;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Long)
    private Long tmId;
    @Field(type = FieldType.Keyword)
    private String tmName;
    @Field(type = FieldType.Keyword)
    private String tmLogoUrl;
    @Field(type = FieldType.Long)
    private Long firstLevelCategoryId;
    @Field(type = FieldType.Keyword)
    private String firstLevelCategoryName;
    @Field(type = FieldType.Long)
    private Long secondLevelCategoryId;
    @Field(type = FieldType.Keyword)
    private String secondLevelCategoryName;
    @Field(type = FieldType.Long)
    private Long thirdLevelCategoryId;
    @Field(type = FieldType.Keyword)
    private String thirdLevelCategoryName;
    @Field(type = FieldType.Long)
    private Long hotScore = 0L;
    @Field(type = FieldType.Nested)
    private List<SearchAttribute> attrs;
}
