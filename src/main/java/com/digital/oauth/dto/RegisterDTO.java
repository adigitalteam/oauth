package com.digital.oauth.dto;

import com.digital.oauth.entity.AuthToken;
import com.digital.oauth.entity.AuthUser;
import lombok.Data;

@Data
public class RegisterDTO {
    private AuthUser authUser;
    private AuthToken authToken;
}
