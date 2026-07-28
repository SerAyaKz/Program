package kz.com.SerAya.service.impl;

import kz.com.SerAya.dto.CourseDto;
import kz.com.SerAya.dto.UserDto;
import kz.com.SerAya.entity.User;
import kz.com.SerAya.repository.UserRepository;
import kz.com.SerAya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Find a user by their Firebase UID
     */
    @Override
    public Optional<User> findByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    /**
     * Find a user by their email
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Create or update a user after Firebase authentication
     */
@Override
    public User createOrUpdateUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByUid(userDto.getUid());

        if (existingUser.isPresent() ) {
            User user = existingUser.get();
            user.setDisplayName(userDto.getDisplayName());
//            user.setEmail(userDto.getEmail());
            user.setPhotoUrl(userDto.getPhotoUrl());
            user.setFullNameKz(userDto.getFullNameKz());
            user.setFullNameEn(userDto.getFullNameEn());
            user.setFullNameRu(userDto.getFullNameRu());
            user.setJobTitleEn(userDto.getJobTitleEn());
            user.setJobTitleKz(userDto.getJobTitleKz());
            user.setJobTitleRu(userDto.getJobTitleRu());
            user.setLastLogin(LocalDateTime.now());
            user.setRoleId(userDto.getRoleId());
            return userRepository.save(user);
        } else {
            User newUser = User.builder()
                    .uid(userDto.getUid())
                    .displayName(userDto.getDisplayName())
                    .email(userDto.getEmail())
                    .photoUrl(userDto.getPhotoUrl())
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .roleId(userDto.getRoleId())
                    .build();
            return userRepository.save(newUser);
        }
    }





    @Override
    public Integer save(UserDto dto) {
        return 0;
    }

    @Override
    public List<UserDto> findAll() {
        return List.of();
    }

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, UserDto dto) {

    }
}