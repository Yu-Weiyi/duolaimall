package pers.wayease.duolaimall.email.service;

import org.springframework.lang.Nullable;

import javax.mail.MessagingException;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.email.service
 * @name EmailService
 * @description Email service interface.
 * @since 2024-10-05 20:44
 */
public interface EmailService {

    void testConnection();

    void test(@Nullable String to) throws MessagingException;
}
