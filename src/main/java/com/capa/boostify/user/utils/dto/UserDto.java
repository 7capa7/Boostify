package com.capa.boostify.user.utils.dto;

import com.capa.boostify.user.utils.Role;

public record UserDto(String id, String email, Role role) {
}
