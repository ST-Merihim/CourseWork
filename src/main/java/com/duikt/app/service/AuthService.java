package com.duikt.app.service;


import com.duikt.app.domain.UserDTO;
import com.duikt.app.domain.auth.LoginDTO;
import com.duikt.app.domain.auth.RegisterDTO;

public interface AuthService {
    UserDTO loginUser(LoginDTO request);
    UserDTO registerUser(RegisterDTO request);
    String generateToken(UserDTO user);
}
