package com.estf.edoctorat.config;

import lombok.Data;

@Data
public class GoogleUserInfo {
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
}