package pers.wayease.duolaimall.user.service;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.service
 * @name JwtAuthService
 * @description JWT auth service interface.
 * @since 2024-10-13 14:07
 */
public interface JwtAuthService {

    public String generateToken(Long userId, String loginIp);

    public Long validateToken(String jwtToken);
}
