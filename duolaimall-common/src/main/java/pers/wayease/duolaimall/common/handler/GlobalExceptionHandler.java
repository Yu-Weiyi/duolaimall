package pers.wayease.duolaimall.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.wayease.duolaimall.common.exception.BaseException;
import pers.wayease.duolaimall.common.result.Result;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.handler
 * @name GlobalExceptionHandler
 * @description Global exception handler class.
 * @since 2024-10-05 20:06
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ResponseBody
    @ExceptionHandler(BaseException.class)
    public Result error(BaseException e) {
        if (e.getCodeEnum() == null) {
            return Result.fail(e.getMessage());
        }
        return Result.build(null, e.getCodeEnum());
    }
}
