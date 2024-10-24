package com.smartexpense.smart_expense_tracker.controller;

import java.util.Set;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ApiResponse<UserDTO> createUser(@RequestBody @Valid UserDTO dto) {
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.create(dto));

        return apiResponse;
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserDTO> getMyInfo() {
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());

        return apiResponse;
    }

    @GetMapping("/{username}")
    public ApiResponse<UserDTO> getUser(@PathVariable("username") String username) {
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.get(username));

        return apiResponse;
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserDTO> updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO dto) {
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.update(userId, dto));

        return apiResponse;
    }

    @GetMapping("/checkFamily")
    public ApiResponse<Boolean> checkUserHasFamily() {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.checkUserHasFamily());

        return apiResponse;
    }

    @GetMapping("/username")
    public ApiResponse<Set<String>> findAllUserByFamily() {
        ApiResponse<Set<String>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.findAllUserByFamily());

        return apiResponse;
    }
}
