package testApplication.service.impl;

import org.example.di_container.annotation.Service;
import testApplication.entity.Gift;
import testApplication.entity.Person;
import testApplication.service.GiftService;

@Service
public class LastDayGiftInServiceImpl implements GiftService {

    private static final String TEEN_RESP_GOOD = "Кайфарики";
    private static final String TEEN_RESP_BAD = "ЫЫЫЫЫ это что за deam";
    private static final String ADULT_RESP = "Спасибо за подарок";
    private static final String CHILD_RESP_GOOD = "ББББББББББББББББББББ";
    private static final String CHILD_RESP_BAD = "ААААААААААААААААААА";

    @Override
    public Gift buyGift() {
        return Gift.getSimpleGift();
    }

    @Override
    public void presentGift(Person child, Gift gift) {
        if (child.getAge() > 25) {
            System.out.println(ADULT_RESP);
        } else if (child.getAge() > 15) {
            switch (gift.getType()) {
                case BAD_GIFT -> System.out.println(TEEN_RESP_BAD);
                case GOOD_GIFT -> System.out.println(TEEN_RESP_GOOD);
            }
        } else {
            switch (gift.getType()) {
                case BAD_GIFT -> System.out.println(CHILD_RESP_BAD);
                case GOOD_GIFT -> System.out.println(CHILD_RESP_GOOD);
            }
        }
    }

}
