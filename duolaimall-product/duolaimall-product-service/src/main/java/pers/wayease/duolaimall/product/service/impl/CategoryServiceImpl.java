package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.common.aop.annotation.Cache;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.product.converter.CategoryConverter;
import pers.wayease.duolaimall.product.converter.TrademarkConverter;
import pers.wayease.duolaimall.product.mapper.CategoryTrademarkMapper;
import pers.wayease.duolaimall.product.mapper.FirstLevelCategoryMapper;
import pers.wayease.duolaimall.product.mapper.SecondLevelCategoryMapper;
import pers.wayease.duolaimall.product.mapper.ThirdLevelCategoryMapper;
import pers.wayease.duolaimall.product.pojo.dto.*;
import pers.wayease.duolaimall.product.pojo.model.*;
import pers.wayease.duolaimall.product.pojo.param.CategoryTrademarkParam;
import pers.wayease.duolaimall.product.service.CategoryService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.2
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name CategoryServiceImpl
 * @description Category service implement class.
 * @since 2024-10-06 20:27
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private FirstLevelCategoryMapper firstLevelCategoryMapper;
    @Autowired
    private SecondLevelCategoryMapper secondLevelCategoryMapper;
    @Autowired
    private ThirdLevelCategoryMapper thirdLevelCategoryMapper;
    @Autowired
    private CategoryTrademarkMapper categoryTrademarkMapper;

    @Autowired
    private CategoryConverter categoryConverter;
    @Autowired
    private TrademarkConverter trademarkConverter;

    @Override
    @Cache(prefix = RedisConstant.FIRST_LEVEL_CATRGORY)
    public List<FirstLevelCategoryDto> getFirstLevelCategory() {
        List<FirstLevelCategory> firstLevelCategoryList = firstLevelCategoryMapper.selectList(null);
        return categoryConverter.firstLevelCategoryPoList2DtoList(firstLevelCategoryList);
    }

    @Override
    @Cache(prefix = RedisConstant.SECOND_LEVEL_CATRGORY)
    public List<SecondLevelCategoryDto> getSecondLevelCategory(Long firstLevelCategoryId) {
        LambdaQueryWrapper<SecondLevelCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SecondLevelCategory::getFirstLevelCategoryId, firstLevelCategoryId);
        List<SecondLevelCategory> secondLevelCategoryList = secondLevelCategoryMapper.selectList(lambdaQueryWrapper);
        return categoryConverter.secondLevelCategoryPoList2DtoList(secondLevelCategoryList);
    }

    @Override
    @Cache(prefix = RedisConstant.THIRD_LEVEL_CATRGORY)
    public List<ThirdLevelCategoryDto> getThirdLevelCategory(Long thirdLevelCategoryId) {
        LambdaQueryWrapper<ThirdLevelCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(ThirdLevelCategory::getSecondLevelCategoryId, thirdLevelCategoryId);
        List<ThirdLevelCategory> thirdLevelCategoryList = thirdLevelCategoryMapper.selectList(lambdaQueryWrapper);
        return categoryConverter.thirdLevelCategoryPoList2DtoList(thirdLevelCategoryList);
    }

    @Override
    public void save(CategoryTrademarkParam categoryTrademarkParam) {
        categoryTrademarkParam.getTrademarkIdList().forEach(trademarkId -> {
            categoryTrademarkMapper.insert(new CategoryTrademark(categoryTrademarkParam.getCategory3Id(), trademarkId, null));
        });
    }

    @Override
    public List<TrademarkDto> findTrademarkList(Long thirdLevelCategoryId) {
        List<Trademark> trademarkList = categoryTrademarkMapper.selectListByThirdLevelCategoryId(thirdLevelCategoryId);
        return trademarkConverter.trademarkPoList2DtoList(trademarkList);
    }

    @Override
    public List<TrademarkDto> findUnLinkedTrademarkList(Long thirdLevelCategoryId) {
        List<Trademark> trademarkList = categoryTrademarkMapper.selectListExcludedByThirdLevelCategoryId(thirdLevelCategoryId);
        return trademarkConverter.trademarkPoList2DtoList(trademarkList);
    }

    @Override
    public void remove(Long thirdLevelCategoryId, Long trademarkId) {
        LambdaUpdateWrapper<CategoryTrademark> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .eq(CategoryTrademark::getThirdLevelCategoryId, thirdLevelCategoryId)
                .eq(CategoryTrademark::getTrademarkId, trademarkId);
        categoryTrademarkMapper.delete(lambdaUpdateWrapper);
    }

    @Override
    @Cache(prefix = RedisConstant.CATEGORY_HIERARCHY)
    public CategoryHierarchyDto getCategortHierarchyByThirdLevelCategoryId(Long thirdLevelCategoryId) {
        ThirdLevelCategory thirdLevelCategory = thirdLevelCategoryMapper.selectById(thirdLevelCategoryId);
        SecondLevelCategory secondLevelCategory = secondLevelCategoryMapper.selectById(thirdLevelCategory.getSecondLevelCategoryId());
        FirstLevelCategory firstLevelCategory = firstLevelCategoryMapper.selectById(secondLevelCategory.getFirstLevelCategoryId());
        return buildCategoryHierarchyDto(firstLevelCategory, secondLevelCategory, thirdLevelCategory);
    }

    private static CategoryHierarchyDto buildCategoryHierarchyDto(FirstLevelCategory firstLevelCategory, SecondLevelCategory secondLevelCategory, ThirdLevelCategory thirdLevelCategory) {
        CategoryHierarchyDto categoryHierarchyDto = new CategoryHierarchyDto();
        categoryHierarchyDto.setFirstLevelCategoryId(firstLevelCategory.getId());
        categoryHierarchyDto.setFirstLevelCategoryName(firstLevelCategory.getName());
        categoryHierarchyDto.setSecondLevelCategoryId(secondLevelCategory.getId());
        categoryHierarchyDto.setSecondLevelCategoryName(secondLevelCategory.getName());
        categoryHierarchyDto.setThirdLevelCategoryId(thirdLevelCategory.getId());
        categoryHierarchyDto.setThirdLevelCategoryName(thirdLevelCategory.getName());
        return categoryHierarchyDto;
    }

    @Override
    @Cache(prefix = RedisConstant.CATEGORY_TREE_LIST)
    public List<FirstLevelCategoryNodeDto> getCategoryTreeList() {
        List<FirstLevelCategoryNodeDto> firstLevelCategoryNodeDtoList = firstLevelCategoryMapper.selectCategoryTreeList();
        return firstLevelCategoryNodeDtoList;
    }
}
