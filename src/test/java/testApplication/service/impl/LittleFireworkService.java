package testApplication.service.impl;

import org.example.di_container.annotation.Service;
import testApplication.service.FireworkService;

@Service
public class LittleFireworkService implements FireworkService {

    @Override
    public void start() {
        System.out.println("Заряжаем фейверк");
        System.out.println("ПШШШШШ, бум");
    }

}
