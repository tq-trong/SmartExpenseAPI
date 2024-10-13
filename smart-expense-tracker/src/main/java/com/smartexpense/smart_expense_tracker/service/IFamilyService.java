package com.smartexpense.smart_expense_tracker.service;

import com.smartexpense.smart_expense_tracker.dto.FamilyDTO;

public interface IFamilyService {
    FamilyDTO updateFamily(String familyId, String username);

    FamilyDTO createFamily(FamilyDTO dto);

    void deleteMember(String username);
}
