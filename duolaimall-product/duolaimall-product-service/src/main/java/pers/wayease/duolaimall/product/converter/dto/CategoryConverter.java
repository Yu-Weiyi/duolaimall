package pers.wayease.duolaimall.product.converter.dto;

import org.mapstruct.Mapper;
import pers.wayease.duolaimall.product.pojo.dto.FirstLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.dto.SecondLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.dto.ThirdLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.model.FirstLevelCategory;
import pers.wayease.duolaimall.product.pojo.model.SecondLevelCategory;
import pers.wayease.duolaimall.product.pojo.model.ThirdLevelCategory;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.converter.dto
 * @name CategoryConverter
 * @description Category converter interface.
 * @since 2024-10-06 21:25
 */
@Mapper(componentModel = "spring")
public interface CategoryConverter {

    FirstLevelCategoryDto firstLevelCategoryPo2Dto(FirstLevelCategory firstLevelCategory);

    List<FirstLevelCategoryDto> firstLevelCategoryPoList2DtoList(List<FirstLevelCategory> firstLevelCategoryList);

    SecondLevelCategoryDto secondLevelCategoryPo2Dto(SecondLevelCategory secondLevelCategory);

    List<SecondLevelCategoryDto> secondLevelCategoryPoList2DtoList(List<SecondLevelCategory> secondLevelCategoryList);

    ThirdLevelCategoryDto thirdLevelCategoryPo2Dto(ThirdLevelCategory thirdLevelCategory);

    List<ThirdLevelCategoryDto> thirdLevelCategoryPoList2DtoList(List<ThirdLevelCategory> thirdLevelCategoryList);
}
