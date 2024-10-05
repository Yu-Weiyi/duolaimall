package pers.wayease.duolaimall.common.exception;

import pers.wayease.duolaimall.common.constant.CodeEnum;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.exception
 * @name ThirdPartyException
 * @description Third party exception class.
 * @since 2024-10-05 20:04
 */
public class ThirdPartyException extends BaseException {

    public ThirdPartyException(CodeEnum resultCodeEnum) {
        super(resultCodeEnum);
    }
}
