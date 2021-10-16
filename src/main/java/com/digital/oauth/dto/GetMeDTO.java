package com.digital.oauth.dto;

import com.digital.oauth.enums.AuthUserStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMeDTO {
    String username;

    String email;

    String phone;

    String inn;

    Long id;

    List<String> authorities;

    AuthUserStatusEnum status;

    FidoIdentityCustomerResponseRequestBodyDTO fidoGspIdentity;

    String avatar;

    String personalIdentificationNumber;

    String passport;

    String avatarIconType;

}
