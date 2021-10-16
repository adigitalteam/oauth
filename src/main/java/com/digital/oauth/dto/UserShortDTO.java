package com.digital.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserShortDTO {
    private Long id;

    private String userName;

    private String email;

    private String phone;

    private String inn;

    private String passport;

    private String personalIdentificationNumber;

    private FidoIdentityCustomerResponseRequestBodyDTO fidoGspIdentity;

    @JsonIgnore
    private String avatar;
}
