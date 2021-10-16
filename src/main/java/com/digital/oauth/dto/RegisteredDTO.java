package com.digital.oauth.dto;

import lombok.Data;

@Data
public class RegisteredDTO {
    private UserShortDTO user;
    private TokenShortDTO token;

}
