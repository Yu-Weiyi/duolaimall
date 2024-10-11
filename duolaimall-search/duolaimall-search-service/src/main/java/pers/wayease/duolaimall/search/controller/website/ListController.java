package pers.wayease.duolaimall.search.controller.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.search.pojo.dto.SearchPageDto;
import pers.wayease.duolaimall.search.pojo.param.SearchParam;
import pers.wayease.duolaimall.search.service.SearchService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.controller.website
 * @name ListController
 * @description List controller class.
 * @since 2024-10-10 14:02
 */
@RestController
public class ListController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/list")
    public Result<SearchPageDto> List(SearchParam searchParam) {
        SearchPageDto searchDto = searchService.search(searchParam);
        return Result.ok(searchDto);
    }
}
