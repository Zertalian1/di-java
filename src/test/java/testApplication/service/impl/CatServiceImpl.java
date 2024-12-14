package testApplication.service.impl;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Service;
import testApplication.entity.ChristmasTree;
import testApplication.service.CatService;

@Service
public class CatServiceImpl implements CatService {

    @Override
    public void dropTree(@Autowired ChristmasTree tree) {
        System.out.println("Ёлка падает!!! " + tree.getSound() + "\n");
    }
}
