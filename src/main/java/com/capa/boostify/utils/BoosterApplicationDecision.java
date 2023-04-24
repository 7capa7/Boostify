package com.capa.boostify.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoosterApplicationDecision {
    private String boosterApplicationId;

    @JsonDeserialize(using = BoosterApplicationStatusDeserializer.class)
    private ApplicationStatus applicationStatus;

    public boolean isValid() {
        if (boosterApplicationId == null || applicationStatus == null) return false;
        return !boosterApplicationId.isBlank() && !applicationStatus.equals(ApplicationStatus.NONE);
    }
}
