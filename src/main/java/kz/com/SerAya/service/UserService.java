package kz.com.SerAya.service;

import kz.com.SerAya.dto.AuthenticationRequest;
import kz.com.SerAya.dto.AuthenticationResponse;
import kz.com.SerAya.dto.LightUserDto;
import kz.com.SerAya.dto.UserDto;

public interface UserService extends AbstractService<UserDto> {

    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);

    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Integer update(LightUserDto userDto);

    Integer updateUser(UserDto userDto);
}