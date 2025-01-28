package com.estf.edoctorat.config;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String access;
    private String refresh;
    private UserInfo userInfo;

    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private String email;
        private String nom;
        private String prenom;
        private String pathPhoto;
        private List<String> groups;
        private Map<String, Object> misc;
    }
}
