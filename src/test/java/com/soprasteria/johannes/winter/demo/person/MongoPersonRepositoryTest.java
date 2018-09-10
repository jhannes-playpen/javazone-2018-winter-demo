package com.soprasteria.johannes.winter.demo.person;

import com.mongodb.MongoClient;
import org.junit.Before;

public class MongoPersonRepositoryTest extends PersonRepositoryTest {

    private MongoPersonRepository repository;
    private MongoClient client;

    @Before
    public void setupRepository() {
        client = new MongoClient("localhost", 27017);
        repository = new MongoPersonRepository(client.getDatabase("unit_test"));
    }

    @Override
    protected PersonRepository getPersonRepository() {
        return repository;
    }

}
