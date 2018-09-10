package com.soprasteria.johannes.winter.demo.person;

import com.soprasteria.johannes.winter.demo.TestDatabase;

public class JdbcPersonRepositoryTest extends PersonRepositoryTest {

    private JdbcPersonRepository personRepository = new JdbcPersonRepository(TestDatabase.createTestDataSource());

    @Override
    protected PersonRepository getPersonRepository() {
        return personRepository;
    }

}
