package com.practiceproject.EmployeeManagementSystem.service;

import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.UserRegistrationDto;

public interface UserService {
    User save(UserRegistrationDto registrationDto);
}
