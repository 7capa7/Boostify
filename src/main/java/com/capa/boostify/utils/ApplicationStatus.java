package com.capa.boostify.utils;

public enum ApplicationStatus {
    PENDING, ACCEPTED, DECLINED, NONE;

    public static boolean isValidEnumValue(String value) {
        try {
            ApplicationStatus.valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
