package com.digital.oauth.dto;

import com.digital.oauth.entity.AuthPermission;
import com.digital.oauth.entity.AuthRole;
import com.digital.oauth.entity.File;
import com.digital.oauth.enums.AuthUserStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserItemDTO {
    Long id;
    String userName;
    String password;
    String email;
    String phone;
    String passport;
    String inn;
    String personalIdentificationNumber;
    AuthUserStatusEnum status;
    LocalDateTime createdDatetime;
    Set<AuthRole> roles;
    Set<AuthPermission> permissions;
    FidoIdentityCustomerResponseRequestBodyDTO fidoGspIdentity;
    File avatar;
    String avatarUrl;
    String avatarIconType;
}
