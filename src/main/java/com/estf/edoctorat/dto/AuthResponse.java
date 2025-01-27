package com.estf.edoctorat.dto;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import lombok.Data;

@Data
public class AuthResponse {
    private String access;
    private String refresh;
    private UserInfo userInfo;
}
