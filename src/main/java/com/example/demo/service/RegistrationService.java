package com.example.demo.service;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.model.role.UserRole;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public String registerUser(UserDto userDto) {
        return userService.signUpUser(
                new User(
                        userDto.getUsername(),
                        userDto.getPassword(),
                        UserRole.USER
                )
        );
    }

    public String registerAdmin(UserDto userDto) {
        return userService.signUpUser(
                new User(
                        userDto.getUsername(),
                        userDto.getPassword(),
                        UserRole.ADMIN
                )
        );
    }
}
