package com.duikt.app.service.impl;

import com.duikt.app.config.security.jwt.JwtProvider;
import com.duikt.app.domain.UserDTO;
import com.duikt.app.domain.auth.LoginDTO;
import com.duikt.app.domain.auth.RegisterDTO;
import com.duikt.app.domain.enums.Role;
import com.duikt.app.entity.UserEntity;
import com.duikt.app.exception.AuthenticationException;
import com.duikt.app.exception.UserAlreadyExistsException;
import com.duikt.app.repository.UserRepository;
import com.duikt.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(RegisterDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        UserEntity user = userRepository.save(UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .age(request.getAge())
                .gender(request.getGender())
                .weight(request.getWeight())
                .height(request.getHeight())
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build());

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Override
    public UserDTO loginUser(LoginDTO request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public String generateToken(UserDTO user) {
        Role role = userRepository.findByEmail(user.getEmail()).get().getRole();
        return jwtProvider.createToken(user.getEmail(), role);
    }
}
