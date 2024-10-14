package kz.com.SerAya.Service;

import kz.com.SerAya.DTO.AuthenticationRequest;
import kz.com.SerAya.DTO.AuthenticationResponse;
import kz.com.SerAya.DTO.LightUserDto;
import kz.com.SerAya.DTO.UserDto;

public interface UserService extends AbstractService<UserDto> {

    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);

    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Integer update(LightUserDto userDto);

    Integer updateUser(UserDto userDto);
}