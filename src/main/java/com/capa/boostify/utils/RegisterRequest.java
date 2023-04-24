package com.capa.boostify.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;

    public boolean isValid() {
        if (email == null || password == null)
            return false;
        return !email.isBlank() && !password.isBlank();
    }
}
