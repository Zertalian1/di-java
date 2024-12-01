package testApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
public class Person {
    private String name;
    private Long age;

    public static Person getSimlePerson() {
        return new Person("Валера", ThreadLocalRandom.current().nextLong(10,40));
    }
}
