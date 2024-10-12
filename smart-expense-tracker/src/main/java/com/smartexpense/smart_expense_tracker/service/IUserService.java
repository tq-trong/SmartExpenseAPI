package com.smartexpense.smart_expense_tracker.service;

import com.smartexpense.smart_expense_tracker.dto.LogDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IUserService extends IBaseService<UserDTO>{
    UserDTO update(String userId, UserDTO user);
    UserDTO getMyInfo();
    void updateUserRole(UserDTO userDTO, String role);
    Set<UserDTO> getMembers(String search, Pageable pageable);
    long totalMembers(String username, Pageable pageable);
    LogDTO createLog(String username, String description);
}
