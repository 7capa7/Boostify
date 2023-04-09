package com.capa.boostify.boostOrder.utils;

import com.capa.boostify.user.utils.Division;

public record BoostingOrderDto(String id, String accountNickname, String accountPassword, Division actualDivision,
                               Division expectedDivision) {
}
