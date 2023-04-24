package com.capa.boostify.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoosterApplicationRequest {
    @JsonDeserialize(using = DivisionEnumDeserializer.class)
    private Division highestDivision;
    @JsonDeserialize(using = DivisionEnumDeserializer.class)
    private Division actualDivision;
    private Integer inGameHours;
    private Year firstSeasonYear;

    public boolean isValid() {
        if (highestDivision == null || actualDivision == null || inGameHours == null || firstSeasonYear == null)
            return false;
        return !highestDivision.equals(Division.NONE) && !actualDivision.equals(Division.NONE) && inGameHours >= 1 && !firstSeasonYear.isBefore(Year.of(2000));
    }
}
