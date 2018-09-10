package com.soprasteria.johannes.winter.demo.cats;

import com.mongodb.BasicDBObject;
import com.soprasteria.johannes.winter.demo.TestDatabase;
import org.junit.Before;

public class MongoCatRepositoryTest extends CatRepositoryTest {

    private MongoCatRepository repository = new MongoCatRepository(TestDatabase.createMongoDatabase());

    @Before
    public void deleteData() {
        TestDatabase.createMongoDatabase().getCollection("cats").deleteMany(new BasicDBObject());
    }

    @Override
    protected CatRepository getRepository() {
        return repository;
    }

}
