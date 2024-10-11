package pers.wayease.duolaimall.search.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.pojo.dto
 * @name SearchTrademarkDto
 * @description Search trademark DTO class.
 * @since 2024-10-10 14:13
 */
@Data
public class SearchTrademarkDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tmId;
    private String tmName;
    private String tmLogoUrl;
}
