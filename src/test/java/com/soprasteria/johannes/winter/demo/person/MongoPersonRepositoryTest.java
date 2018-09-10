package com.soprasteria.johannes.winter.demo.person;

import com.soprasteria.johannes.winter.demo.TestDatabase;

public class MongoPersonRepositoryTest extends PersonRepositoryTest {

    private MongoPersonRepository repository = new MongoPersonRepository(TestDatabase.createMongoDatabase());

    @Override
    protected PersonRepository getPersonRepository() {
        return repository;
    }

}
