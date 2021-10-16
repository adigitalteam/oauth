package com.digital.oauth.controller;

import com.digital.oauth.dto.ConfirmedDTO;
import com.digital.oauth.dto.ResendConfirmRequestDTO;
import com.digital.oauth.dto.ResetPasswordRequestDTO;
import com.digital.oauth.dto.TokenConfirmDTO;
import com.digital.oauth.entity.AuthToken;
import com.digital.oauth.exceptions.*;
import com.digital.oauth.service.AuthTokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth-token")
public class AuthTokenController {
    @Autowired
    private AuthTokenService authTokenService;

    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.CREATED)
    public ConfirmedDTO confirm(@RequestBody TokenConfirmDTO dto) throws AuthTokenExpiredException, AuthTokenInactiveException, AuthTokenNotFoundException, UserNotFoundException, TokenConfirmDataException {
        return authTokenService.confirm(dto);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PostMapping("/resend-confirm")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken resendConfirm(@RequestBody ResendConfirmRequestDTO dto) throws UserIsActiveException, UserNotFoundException {
        return authTokenService.resendConfirm(dto);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken resendConfirm(@RequestBody ResetPasswordRequestDTO dto) throws UserIsNotActiveException, UserNotFoundException {
        return authTokenService.resetPassword(dto);
    }

}
