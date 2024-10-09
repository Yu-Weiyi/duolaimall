package pers.wayease.duolaimall.product.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.FirstLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.dto.SecondLevelCategoryDto;
import pers.wayease.duolaimall.product.pojo.dto.ThirdLevelCategoryDto;
import pers.wayease.duolaimall.product.service.CategoryService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller.admin
 * @name CategoryController
 * @description Category controller class.
 * @since 2024-10-06 20:04
 */
@RestController
@RequestMapping("/admin/product")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategory1")
    public Result<List<FirstLevelCategoryDto>> getCategory1() {
        List<FirstLevelCategoryDto> firstLevelCategoryDtoList = categoryService.getFirstLevelCategory();
        return Result.ok(firstLevelCategoryDtoList);
    }

    @GetMapping("/getCategory2/{firstLevelCategoryId}")
    public Result<List<SecondLevelCategoryDto>> getCategory2(@PathVariable Long firstLevelCategoryId) {
        List<SecondLevelCategoryDto> secondaryLevelCategoryDtoList = categoryService.getSecondLevelCategory(firstLevelCategoryId);
        return Result.ok(secondaryLevelCategoryDtoList);
    }

    @GetMapping("/getCategory3/{secondLevelCategoryId}")
    public Result<List<ThirdLevelCategoryDto>> getCategory3(@PathVariable Long secondLevelCategoryId) {
        List<ThirdLevelCategoryDto> thirdLevelCategoryDtoList = categoryService.getThirdLevelCategory(secondLevelCategoryId);
        return Result.ok(thirdLevelCategoryDtoList);
    }
}
