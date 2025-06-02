package com.duikt.app.service;


import com.duikt.app.domain.UserDTO;
import com.duikt.app.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> findAllUsers();
    UserDTO createUser(UserDTO userDTO, String password);
    UserDTO findUserById(UUID id) throws UserNotFoundException;
    UserDTO updateUser(UUID id, UserDTO userDTO) throws UserNotFoundException;
    UserDTO updatePassword(UUID id, String password, String passwordConf) throws UserNotFoundException;
    void deleteUser(UUID id) throws UserNotFoundException;
}
