package pers.wayease.duolaimall.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.email.service.EmailService;

import javax.mail.MessagingException;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.email.controller
 * @name EmailController
 * @description Send email controller class.
 * @since 2024-10-05 20:40
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test/connection")
    public Result<Void> testConnection(){
        emailService.testConnection();
        return Result.ok();
    }

    @PostMapping("/test")
    public Result<Void> test() throws MessagingException {
        emailService.test(null);
        return Result.ok();
    }

    @PostMapping("/test/{to}")
    public Result<Void> test(@PathVariable String to) throws MessagingException {
        emailService.test(to);
        return Result.ok();
    }
}
