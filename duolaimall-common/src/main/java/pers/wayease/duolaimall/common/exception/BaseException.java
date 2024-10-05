package pers.wayease.duolaimall.common.exception;

import lombok.Data;
import pers.wayease.duolaimall.common.constant.CodeEnum;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.exception
 * @name BaseException
 * @description Base exception class.
 * @since 2024-10-05 20:01
 */
@Data
public class BaseException extends RuntimeException {

    private Integer code;
    private CodeEnum codeEnum;

    public BaseException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BaseException(CodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.codeEnum = resultCodeEnum;
    }
}
