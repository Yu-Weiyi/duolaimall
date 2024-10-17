package pers.wayease.duolaimall.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.user.pojo.dto.UserAddressDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.client
 * @name UserServiceClient
 * @description User service client interface.
 * @since 2024-10-15 22:43
 */
@FeignClient(name = "wayease-duolaimall-user-service", url = "http://wayease-duolaimall-user-service.wayease-duolaimall.svc.cluster.local:8080")
public interface UserServiceClient {

    @GetMapping("/api/user/getUserAddressListByUserId/{userId}")
    public Result<List<UserAddressDto>> getUserAddressListByUserId(@PathVariable("userId") Long userId);
}
