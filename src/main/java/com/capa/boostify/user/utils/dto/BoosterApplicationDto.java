package com.capa.boostify.user.utils.dto;

import com.capa.boostify.user.entity.booster.BoosterDetails;
import com.capa.boostify.user.utils.BoosterApplicationStatus;

public record BoosterApplicationDto(String id, UserDto userDto, BoosterDetails boosterDetails,
                                    BoosterApplicationStatus boosterApplicationStatus) {
}
