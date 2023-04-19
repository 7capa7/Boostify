package com.capa.boostify.user.utils;

public enum BoosterApplicationStatus {
    PENDING, ACCEPTED, DECLINED, NONE;

    public static boolean isValidEnumValue(String value) {
        try {
            BoosterApplicationStatus.valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
