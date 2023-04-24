package com.capa.boostify.utils.dto;

import com.capa.boostify.utils.Role;

public record UserDto(String id, String email, Role role) {
}
