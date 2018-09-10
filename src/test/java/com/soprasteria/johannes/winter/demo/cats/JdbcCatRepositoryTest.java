package com.soprasteria.johannes.winter.demo.cats;

import com.soprasteria.johannes.winter.demo.TestDatabase;

public class JdbcCatRepositoryTest extends CatRepositoryTest {

    private CatRepository repository = new JdbcCatRepository(TestDatabase.createTestDataSource());

    @Override
    protected CatRepository getRepository() {
        return repository;
    }

}
