package com.smartexpense.smart_expense_tracker.service.impl;

import com.smartexpense.smart_expense_tracker.converter.FamilyConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.FamilyDTO;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.FamilyRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IFamilyService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FamilyService implements IFamilyService {
    @Autowired
    private FamilyConverter familyConverter;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FamilyDTO updateFamily(String familyId, String username) {
        Family family = familyRepository.findById(familyId).orElseThrow();

        Set<User> users =family.getUser();

        users.add(userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        family.setUser(users);

        familyRepository.save(family);

        return familyConverter.toDTO(family);
    }

    @Override
    public FamilyDTO createFamily(FamilyDTO dto) {
        Family family = familyConverter.toEntity(dto);
        Set<String> userIds = dto.getUsers().stream()
                .map(UserDTO::getId)
                .collect(Collectors.toSet());
        Set<User>  setUsers = userRepository.findAllByIdIn(userIds);
        family.setUser(setUsers);

        familyRepository.save(family);

        return familyConverter.toDTO(family);
    }
}
