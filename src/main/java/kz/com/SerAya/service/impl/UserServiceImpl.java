package kz.com.SerAya.service.impl;

import kz.com.SerAya.config.JwtUtils;
import kz.com.SerAya.dto.AuthenticationRequest;
import kz.com.SerAya.dto.AuthenticationResponse;
import kz.com.SerAya.dto.LightUserDto;
import kz.com.SerAya.dto.UserDto;
import kz.com.SerAya.entity.Role;
import kz.com.SerAya.entity.User;
import kz.com.SerAya.repository.RoleRepository;
import kz.com.SerAya.repository.UserRepository;
import kz.com.SerAya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;


    @Override
    public Integer save(UserDto dto) {
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user).getId();
    }



    @Override
    @Transactional
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return repository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check before delete
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, UserDto userDto) {

    }

    @Override
    @Transactional
    public Integer validateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));



        user.setActive(true);
        repository.save(user);
        return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        user.setActive(false);
        repository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(
                findOrCreateRole(ROLE_USER)
        );
        user.setActive(true);
        user.setCreatedDate(LocalDateTime.now());
        var savedUser = repository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname() + " " + savedUser.getLastname());
        String token = jwtUtils.generateToken(savedUser, claims);


        return AuthenticationResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .expiresIn(3600)
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String email=request.getEmail();
        String password=request.getPassword();
        UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(email,password);
        System.out.println(token1);
        authManager.authenticate(token1);
        final User user = repository.findByEmail(request.getEmail()).get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());
        final String token = jwtUtils.generateToken(user, claims);

        return AuthenticationResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .expiresIn(3600)
                .token(token)
                .build();
    }

    @Override
    public Integer update(LightUserDto userDto) {
        User user = LightUserDto.toEntity(userDto);
        return repository.save(user).getId();
    }

    @Override
    public Integer updateUser(UserDto userDto) {


        User user = repository.findById(userDto.getId()).get();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        repository.save(user);

        return user.getId();
    }

    private Role findOrCreateRole(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElse(null);
        if (role == null) {
            return roleRepository.save(
                    Role.builder()
                            .name(roleName)
                            .build()
            );
        }
        return role;
    }

}
