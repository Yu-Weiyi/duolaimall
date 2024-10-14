package pers.wayease.duolaimall.user.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.pojo.dto
 * @name UserAddressDto
 * @description User address DTO class.
 * @since 2024-10-14 09:43
 */
@Data
public class UserAddressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    Long id;
    private String userAddress;
    private Long userId;
    private String consignee;
    private String phoneNum;
    private String isDefault;
}
