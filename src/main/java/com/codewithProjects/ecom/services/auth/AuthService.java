package com.codewithProjects.ecom.services.auth;

import com.codewithProjects.ecom.dto.SignupRequest;
import com.codewithProjects.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
    Boolean hasUserWithEmail(String email);
}
