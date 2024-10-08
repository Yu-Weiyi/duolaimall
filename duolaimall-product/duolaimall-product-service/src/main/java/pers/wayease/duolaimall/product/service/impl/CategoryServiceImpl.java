package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.product.converter.CategoryConverter;
import pers.wayease.duolaimall.product.mapper.FirstLevelCategoryMapper;
import pers.wayease.duolaimall.product.mapper.SecondLevelCategoryMapper;
import pers.wayease.duolaimall.product.mapper.ThirdLevelCategoryMapper;
import pers.wayease.duolaimall.product.pojo.dto.FirstLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.dto.SecondLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.dto.ThirdLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.model.FirstLevelCategory;
import pers.wayease.duolaimall.product.pojo.model.SecondLevelCategory;
import pers.wayease.duolaimall.product.pojo.model.ThirdLevelCategory;
import pers.wayease.duolaimall.product.service.CategoryService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
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
    private CategoryConverter categoryConverter;

    @Override
    public List<FirstLevelCategoryDto> getFirstLevelCategory() {
        List<FirstLevelCategory> firstLevelCategoryList = firstLevelCategoryMapper.selectList(null);
        return categoryConverter.firstLevelCategoryPoList2DtoList(firstLevelCategoryList);
    }

    @Override
    public List<SecondLevelCategoryDto> getSecondLevelCategory(Long firstLevelCategoryId) {
        LambdaQueryWrapper<SecondLevelCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SecondLevelCategory::getFirstLevelCategoryId, firstLevelCategoryId);
        List<SecondLevelCategory> secondLevelCategoryList = secondLevelCategoryMapper.selectList(lambdaQueryWrapper);
        return categoryConverter.secondLevelCategoryPoList2DtoList(secondLevelCategoryList);
    }

    @Override
    public List<ThirdLevelCategoryDto> getThirdLevelCategory(Long thirdLevelCategoryId) {
        LambdaQueryWrapper<ThirdLevelCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(ThirdLevelCategory::getSecondLevelCategoryId, thirdLevelCategoryId);
        List<ThirdLevelCategory> thirdLevelCategoryList = thirdLevelCategoryMapper.selectList(lambdaQueryWrapper);
        return categoryConverter.thirdLevelCategoryPoList2DtoList(thirdLevelCategoryList);
    }
}
