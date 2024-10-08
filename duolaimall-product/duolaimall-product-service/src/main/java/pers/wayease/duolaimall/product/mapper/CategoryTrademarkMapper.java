package pers.wayease.duolaimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.product.pojo.model.CategoryTrademark;
import pers.wayease.duolaimall.product.pojo.model.Trademark;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mapper
 * @name CategoryTrademarkMapper
 * @description Category trademark mapper interface.
 * @since 2024-10-08 19:39
 */
public interface CategoryTrademarkMapper extends BaseMapper<CategoryTrademark> {

    List<Trademark> selectListByThirdLevelCategoryId(@Param("thirdLevelCategoryId") Long thirdLevelCategoryId);

    List<Trademark> selectListExcludedByThirdLevelCategoryId(@Param("thirdLevelCategoryId") Long thirdLevelCategoryId);
}
