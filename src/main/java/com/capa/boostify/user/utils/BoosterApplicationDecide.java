package com.capa.boostify.user.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoosterApplicationDecide {
    private String boosterApplicationId;

    @JsonDeserialize(using = BoosterApplicationStatusDeserializer.class)
    private BoosterApplicationStatus boosterApplicationStatus;

    public boolean isValid() {
        if (boosterApplicationId == null || boosterApplicationStatus == null) return false;
        return !boosterApplicationId.isBlank() && !boosterApplicationStatus.equals(BoosterApplicationStatus.NONE);
    }
}
