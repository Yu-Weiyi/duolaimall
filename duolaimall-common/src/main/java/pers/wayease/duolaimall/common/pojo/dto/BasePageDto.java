package pers.wayease.duolaimall.common.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.pojo.dto
 * @name BasePageDto
 * @description Base page DTO class.
 * @since 2024-10-08 17:39
 */
@Data
public class BasePageDto<T> {

    private List<T> records;
    private Integer total;
}
