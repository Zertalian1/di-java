package org.example.test.entity;

import lombok.Getter;

@Getter
public enum GiftType {
    BAD_GIFT("Плохой подарок"),
    GOOD_GIFT("Хороший подарок");

    private final String name;

    GiftType(String name) {
        this.name = name;
    }
}
