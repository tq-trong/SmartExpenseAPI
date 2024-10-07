package com.smartexpense.smart_expense_tracker.service.impl;

import com.smartexpense.smart_expense_tracker.converter.RoleConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.entity.Role;
import com.smartexpense.smart_expense_tracker.enums.Roles;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.RoleRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleConverter roleConverter;


    @Override
    public UserDTO create(UserDTO dto) {
        User user = new User();
        if(userRepository.existsByUsername(dto.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        user = userConverter.toEntity(dto);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> role = new HashSet<>();
        Role adminRole = roleRepository.findByName(Roles.ADMIN.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        role.add(adminRole);
        user.setRoles(role);

        userRepository.save(user);
        return userConverter.toDTO(user);
    }

    @Override
    public UserDTO getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTED));

        return userConverter.toDTO(user);
    }

    @PreAuthorize("returnObject.username == authentication.name")
    @Override
    public UserDTO get(String id) {
        return userConverter.toDTO(userRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserDTO update(String userId, UserDTO userDTO) {
        User user = userConverter.toEntity(userRepository.findById(userId).get(), userDTO);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var roles = roleRepository.findAllById(userDTO.getRoles());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);

        return userConverter.toDTO(user);
    }
}
