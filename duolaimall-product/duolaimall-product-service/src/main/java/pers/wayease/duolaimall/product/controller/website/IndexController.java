package pers.wayease.duolaimall.product.controller.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.FirstLevelCategoryNodeDto;
import pers.wayease.duolaimall.product.service.CategoryService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller.website
 * @name IndexController
 * @description Index controller class.
 * @since 2024-10-10 02:14
 */
@RestController
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/index")
    public Result<List<FirstLevelCategoryNodeDto>> getBaseCategoryList() {
        List<FirstLevelCategoryNodeDto> categoryTreeList = categoryService.getCategoryTreeList();
        return Result.ok(categoryTreeList);
    }
}
