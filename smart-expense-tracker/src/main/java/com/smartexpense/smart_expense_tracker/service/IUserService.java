package com.smartexpense.smart_expense_tracker.service;

import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.smartexpense.smart_expense_tracker.dto.LogDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;

public interface IUserService extends IBaseService<UserDTO> {
    UserDTO update(String userId, UserDTO user);

    UserDTO getMyInfo();

    void updateUserRole(UserDTO userDTO, String role);

    Set<UserDTO> getMembers(String search, Pageable pageable);

    long totalMembers(String username, Pageable pageable);

    LogDTO createLog(String username, String description);

    boolean checkUserHasFamily();

    Set<String> findAllUserByFamily();
}
