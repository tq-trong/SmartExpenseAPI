package com.smartexpense.smart_expense_tracker.service;

import com.smartexpense.smart_expense_tracker.dto.FamilyDTO;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.User;

public interface IFamilyService {
    FamilyDTO updateFamily(String familyId, String username);
    FamilyDTO createFamily(FamilyDTO dto);
    void deleteMember(String username);
}
