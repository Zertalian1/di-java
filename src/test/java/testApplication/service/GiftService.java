package testApplication.service;

import testApplication.entity.Person;
import testApplication.entity.Gift;

public interface GiftService {

    Gift buyGift();

    void presentGift(Person person, Gift gift);

}
