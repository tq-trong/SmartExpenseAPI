package com.smartexpense.smart_expense_tracker.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.smartexpense.smart_expense_tracker.dto.request.AuthenticationRequest;
import com.smartexpense.smart_expense_tracker.dto.request.IntrospectRequest;
import com.smartexpense.smart_expense_tracker.dto.request.LogoutRequest;
import com.smartexpense.smart_expense_tracker.dto.request.RefreshRequest;
import com.smartexpense.smart_expense_tracker.dto.response.AuthenticationResponse;
import com.smartexpense.smart_expense_tracker.dto.response.IntrospectResponse;

public interface IAuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;
}
