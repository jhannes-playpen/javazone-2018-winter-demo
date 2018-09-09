package com.soprasteria.johannes.winter.demo.person;

import java.util.List;

public interface PersonRepository {

    List<Person> listAll();

    Person create(Person person);

}
