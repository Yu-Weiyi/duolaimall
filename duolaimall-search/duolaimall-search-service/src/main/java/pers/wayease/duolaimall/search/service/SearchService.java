package pers.wayease.duolaimall.search.service;

import pers.wayease.duolaimall.search.pojo.dto.SearchPageDto;
import pers.wayease.duolaimall.search.pojo.param.SearchParam;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.service
 * @name SearchService
 * @description Search service interface.
 * @since 2024-10-10 14:21
 */
public interface SearchService {

    SearchPageDto search(SearchParam searchParam);
}
