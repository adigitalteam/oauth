package com.digital.oauth.dto;

import com.digital.oauth.enums.AuthTokenDataTypeEnum;
import com.digital.oauth.enums.AuthTokenTypeEnum;
import lombok.Data;

@Data
public class CreateTokenDTO {
    private String data;
    private AuthTokenDataTypeEnum data_type;
    private AuthTokenTypeEnum type;
    private String identityId;
}
