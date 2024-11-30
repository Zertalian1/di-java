package org.example.test.service;

import org.example.test.entity.Person;
import org.example.test.entity.Gift;

public interface GiftService {

    Gift buyGift();

    void presentGift(Person person, Gift gift);

}
