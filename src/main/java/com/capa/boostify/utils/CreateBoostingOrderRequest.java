package com.capa.boostify.utils;

import com.capa.boostify.utils.Division;
import com.capa.boostify.utils.DivisionEnumDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoostingOrderRequest {
    private String accountNickname;
    private String accountPassword;
    @JsonDeserialize(using = DivisionEnumDeserializer.class)
    private Division actualDivision;
    @JsonDeserialize(using = DivisionEnumDeserializer.class)
    private Division expectedDivision;

    public boolean isValid() {
        if (accountNickname == null || accountPassword == null || actualDivision == null || expectedDivision == null)
            return false;
        return !accountNickname.isBlank() && !accountPassword.isBlank() && !actualDivision.equals(Division.NONE) && !expectedDivision.equals(Division.NONE);
    }
}
