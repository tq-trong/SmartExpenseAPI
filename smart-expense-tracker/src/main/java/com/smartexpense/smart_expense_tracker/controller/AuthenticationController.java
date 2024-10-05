package com.smartexpense.smart_expense_tracker.controller;

import com.nimbusds.jose.JOSEException;
import com.smartexpense.smart_expense_tracker.dto.request.AuthenticationRequest;
import com.smartexpense.smart_expense_tracker.dto.request.IntrospectRequest;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.dto.response.AuthenticationResponse;
import com.smartexpense.smart_expense_tracker.dto.response.IntrospectResponse;
import com.smartexpense.smart_expense_tracker.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authentication(request);

        ApiResponse<AuthenticationResponse> response = new ApiResponse<>();
        response.setResult(result);
        return response;
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);

        ApiResponse<IntrospectResponse> response = new ApiResponse<>();
        response.setResult(result);
        return response;
    }
}
