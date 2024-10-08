package pers.wayease.duolaimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.product.pojo.model.PlatformAttributeInfo;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mapper
 * @name PlatformAttributeInfoMapper
 * @description Platform attribute info mapper interface.
 * @since 2024-10-08 14:37
 */
public interface PlatformAttributeInfoMapper extends BaseMapper<PlatformAttributeInfo> {

    List<PlatformAttributeInfo> selectObjectList(
            @Param("firstLevelCategoryId") Long firstLevelCategoryId,
            @Param("secondLevelCategoryId") Long secondLevelCategoryId,
            @Param("thirdLevelCategoryId") Long thirdLevelCategoryId
    );
}
