package pers.wayease.duolaimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.wayease.duolaimall.product.pojo.dto.FirstLevelCategoryNodeDto;
import pers.wayease.duolaimall.product.pojo.model.FirstLevelCategory;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mapper
 * @name FirstLevelCategoryMapper
 * @description First level category mapper interface.
 * @since 2024-10-06 20:30
 */
public interface FirstLevelCategoryMapper extends BaseMapper<FirstLevelCategory> {

    List<FirstLevelCategoryNodeDto> selectCategoryTreeList();
}
