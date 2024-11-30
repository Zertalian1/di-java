package org.example.test.entity;

import lombok.Getter;

@Getter
public enum GiftType {
    BAD_GIFT("BAD"),
    GOOD_GIFT("GOOD");

    private final String name;

    GiftType(String name) {
        this.name = name;
    }
}
