package org.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Gift {
    private String name;
    private BigDecimal price;
    private GiftType type;

    public static Gift getSimpleGift() {
        return new Gift("Уголь", BigDecimal.valueOf(4999.9), GiftType.BAD_GIFT);
    }
}
