package com.practiceproject.EmployeeManagementSystem.service;

import com.practiceproject.EmployeeManagementSystem.dto.UserRegistrationDto;
import com.practiceproject.EmployeeManagementSystem.entity.User;

public interface UserService {
    User save(UserRegistrationDto registrationDto);
}
