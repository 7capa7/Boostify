package com.capa.boostify.user.utils;

public enum Division {
    IRON,BRONZE,SILVER,GOLD,PLATINUM,DIAMOND,MASTER,CHALLENGER,NONE;

    public static boolean isValidEnumValue(String value) {
        try {
            Division.valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
