package com.cyberschnitzel.Domain.Entities;

public enum BloodType {
    ZERO, A, B, AB;

    public static BloodType getByString(String bloodtype) {
        bloodtype = bloodtype.toUpperCase();
        switch (bloodtype) {
            case "ZERO":
                return ZERO;
            case "A":
                return A;
            case "B":
                return B;
            case "AB":
                return AB;
            default:
                return null;
        }
    }
}
