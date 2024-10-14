package pers.wayease.duolaimall.user.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.pojo.dto
 * @name UserLoginDto
 * @description User login DTO class.
 * @since 2024-10-12 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    String nickName;
    String token;
}
