package com.capa.boostify.utils.dto;

import com.capa.boostify.entity.BoosterDetails;
import com.capa.boostify.utils.BoosterApplicationStatus;

public record BoosterApplicationDto(String id, UserDto userDto, BoosterDetails boosterDetails,
                                    BoosterApplicationStatus boosterApplicationStatus) {
}
