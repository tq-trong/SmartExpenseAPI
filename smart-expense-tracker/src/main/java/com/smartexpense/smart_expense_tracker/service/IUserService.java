package com.smartexpense.smart_expense_tracker.service;

import com.smartexpense.smart_expense_tracker.dto.UserDTO;

public interface IUserService extends IBaseService<UserDTO>{
    UserDTO update(String userId, UserDTO user);
    UserDTO getMyInfo();
}
