package com.smartexpense.smart_expense_tracker.service;

import com.nimbusds.jose.JOSEException;
import com.smartexpense.smart_expense_tracker.dto.request.AuthenticationRequest;
import com.smartexpense.smart_expense_tracker.dto.request.IntrospectRequest;
import com.smartexpense.smart_expense_tracker.dto.response.AuthenticationResponse;
import com.smartexpense.smart_expense_tracker.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
