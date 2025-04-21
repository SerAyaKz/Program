package kz.com.SerAya.service;

import kz.com.SerAya.dto.UserDto;
import kz.com.SerAya.entity.User;

import java.util.Optional;

public interface UserService extends AbstractService<UserDto> {

    User createOrUpdateUser(UserDto userDto);
    Optional<User> findByEmail(String email);
    Optional<User> findByUid(String uid);
}