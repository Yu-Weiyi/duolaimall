package pers.wayease.duolaimall.product.service;

import pers.wayease.duolaimall.product.pojo.dto.*;
import pers.wayease.duolaimall.product.pojo.param.CategoryTrademarkParam;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.2
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name CategoryService
 * @description Category service interface.
 * @since 2024-10-06 20:23
 */
public interface CategoryService {

    List<FirstLevelCategoryDto> getFirstLevelCategory();

    List<SecondLevelCategoryDto> getSecondLevelCategory(Long firstLevelCategoryId);

    List<ThirdLevelCategoryDto> getThirdLevelCategory(Long thirdLevelCategoryId);

    void save(CategoryTrademarkParam categoryTrademarkParam);

    List<TrademarkDto> findTrademarkList(Long thirdLevelCategoryId);

    List<TrademarkDto> findUnLinkedTrademarkList(Long thirdLevelCategoryId);

    void remove(Long thirdLevelCategoryId, Long trademarkId);

    CategoryHierarchyDto getCategortHierarchyByThirdLevelCategoryId(Long thirdLevelCategoryId);

    List<FirstLevelCategoryNodeDto> getCategoryTreeList();
}
