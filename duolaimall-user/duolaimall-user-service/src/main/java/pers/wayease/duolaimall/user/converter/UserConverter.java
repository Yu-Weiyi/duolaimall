package pers.wayease.duolaimall.user.converter;

import org.mapstruct.Mapper;
import pers.wayease.duolaimall.user.pojo.dto.UserAddressDto;
import pers.wayease.duolaimall.user.pojo.model.UserAddress;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.converter
 * @name UserConverter
 * @description User converter interface.
 * @since 2024-10-14 09:52
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserAddressDto userAddressPo2Dto(UserAddress userAddress);

    List<UserAddressDto> userAddressPoList2DtoList(List<UserAddress> userAddressList);
}
