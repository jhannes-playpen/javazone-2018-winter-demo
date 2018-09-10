package com.soprasteria.johannes.winter.demo.cats;

import java.util.List;

public interface CatRepository {

    List<Cat> listAll();

    Cat retrieve(String id);

    void create(Cat cat);

}
