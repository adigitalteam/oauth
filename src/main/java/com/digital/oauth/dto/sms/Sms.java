package com.digital.oauth.dto.sms;

import lombok.Data;

@Data
public class Sms {
    int originator;
    Content content;
}
