package com.soprasteria.johannes.winter.demo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

public class TestDatabase {

    public static JdbcDataSource createTestDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:personRepository;DB_CLOSE_DELAY=-1");

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
        return dataSource;
    }

    private static MongoClient mongoClient = new MongoClient("localhost", 27017);


    public static MongoDatabase createMongoDatabase() {
        return mongoClient.getDatabase("unit_test");
    }

}
