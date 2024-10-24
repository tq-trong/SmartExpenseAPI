package com.smartexpense.smart_expense_tracker.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.LogConverter;
import com.smartexpense.smart_expense_tracker.converter.RoleConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.LogDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.Log;
import com.smartexpense.smart_expense_tracker.entity.Role;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.enums.Roles;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.FamilyRepository;
import com.smartexpense.smart_expense_tracker.repository.LogRepository;
import com.smartexpense.smart_expense_tracker.repository.RoleRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IUserService;

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

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private LogConverter logConverter;

    @Autowired
    private LogRepository logRepository;

    @Override
    public UserDTO create(UserDTO dto) {
        User user;
        user = userConverter.toEntity(dto);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> role = new HashSet<>();
        Role adminRole = roleRepository
                .findByName(Roles.USER.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        role.add(adminRole);
        user.setRoles(role);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userConverter.toDTO(user);
    }

    @Override
    public UserDTO getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userConverter.toDTO(user);
    }

    @Override
    public UserDTO get(String username) {
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Family family = familyRepository
                .findByUser(getMyInfo().getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.FAMILY_NOT_EXISTED));

        if (family.getUser().contains(user)) return userConverter.toDTO(user);
        else throw new AppException(ErrorCode.PERMISSION_INVALID);
    }

    @PreAuthorize("returnObject.username == authentication.name")
    @Override
    public UserDTO update(String userId, UserDTO userDTO) {
        User user = userConverter.toEntity(
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)),
                userDTO);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roles =
                userDTO.getRoles().stream().map(roleConverter::toEntity).collect(Collectors.toSet());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);

        return userConverter.toDTO(user);
    }

    @Override
    public void updateUserRole(UserDTO userDTO, String role) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Delete current roles
        user.getRoles().clear();

        // Add new role
        Set<Role> newRoles = new HashSet<>();
        Role roleUpdate = roleRepository.findByName(role).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        newRoles.add(roleUpdate);

        user.setRoles(newRoles);
        userRepository.save(user);
    }

    @Override
    public Set<UserDTO> getMembers(String search, Pageable pageable) {
        return getPageUser(search, pageable).stream().map(userConverter::toDTO).collect(Collectors.toSet());
    }

    public Page<User> getPageUser(String search, Pageable pageable) {
        User user = userRepository
                .findByUsername(getMyInfo().getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Family family = familyRepository
                .findByUser(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.FAMILY_NOT_EXISTED));

        Page<User> users;
        users = userRepository.findUsersExceptOne(search, family.getId(), user.getUsername(), pageable);

        return users;
    }

    @Override
    public long totalMembers(String username, Pageable pageable) {
        return getPageUser(username, pageable).getTotalElements();
    }

    @Override
    public LogDTO createLog(String username, String description) {
        Log log = new Log();
        log.setUser(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        log.setDescription(description);

        logRepository.save(log);
        return logConverter.toDTO(log);
    }

    @Override
    public boolean checkUserHasFamily() {
        User user = userRepository
                .findByUsername(getMyInfo().getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Optional<Family> optionalFamily = familyRepository.findByUser(user.getUsername());
        return optionalFamily.isPresent();
    }

    @Override
    public Set<String> findAllUserByFamily() {
        User user = userRepository
                .findByUsername(getMyInfo().getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Optional<Family> optionalFamily = familyRepository.findByUser(user.getUsername());

        Set<String> setUsers = new HashSet<>();

        if (optionalFamily.isPresent())
            setUsers = userRepository.findAllUserByFamily(optionalFamily.get().getId());
        else setUsers.add(user.getUsername());

        return setUsers;
    }
}
