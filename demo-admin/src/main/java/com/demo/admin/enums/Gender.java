package com.demo.admin.enums;

public enum Gender {

    male("M", "male"), female("F", "Female");
    private String shortName;
    private String fullName;

    Gender(String shortName, String fullName){
        this.shortName = shortName;
        this.fullName = fullName;
    }
    public static Gender fromString(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.shortName.equalsIgnoreCase(value) || gender.fullName.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender value: " + value);
    }
}
