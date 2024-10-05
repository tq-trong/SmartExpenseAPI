package com.smartexpense.smart_expense_tracker.controller;

import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public ApiResponse<UserDTO> createUser(@RequestBody @Valid UserDTO dto) {
        ApiResponse<UserDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.create(dto));

        return apiResponse;
    }

    @GetMapping("/myInfo")
    public UserDTO getMyInfo() {
        return userService.getMyInfo();
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable("userId") String userId) {
        return userService.get(userId);
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO dto) {
        return userService.update(userId,dto);
    }
}
