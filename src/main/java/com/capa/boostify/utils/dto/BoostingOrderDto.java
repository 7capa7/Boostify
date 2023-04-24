package com.capa.boostify.utils.dto;

import com.capa.boostify.utils.Division;

public record BoostingOrderDto(String id, String accountNickname, String accountPassword, Division actualDivision,
                               Division expectedDivision) {
}
