package com.soprasteria.johannes.winter.demo;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.soprasteria.johannes.winter.demo.person.JdbcPersonRepository;
import com.soprasteria.johannes.winter.demo.person.MongoPersonRepository;
import com.soprasteria.johannes.winter.demo.person.PersonContext;
import com.soprasteria.johannes.winter.demo.person.PersonRepository;
import com.soprasteria.johannes.winter.framework.config.ApplicationPropertySource;

public class DemoWinterContext implements HelloContext, PersonContext {

    private ApplicationPropertySource props;

    public DemoWinterContext(ApplicationPropertySource propertySource) {
        this.props = propertySource;
    }

    @Override
    public String getGreeting() {
        return props.required("hello.greeting");
    }

    @Override
    public PersonRepository getPersonRepository() {
        String databaseProvider = props.propertyChoice("personRepository",
                new String[] { "mongo", "jdbc" }, "jdbc");
        if (databaseProvider.equals("mongo")) {
            return getMongoPersonRepository();
        } else {
            return getJdbcPersonRepository();
        }
    }

    private PersonRepository getMongoPersonRepository() {
        return new MongoPersonRepository(getMongoClient().getDatabase(props.property("mongo.database").orElse("test")));
    }

    private PersonRepository getJdbcPersonRepository() {
        return new JdbcPersonRepository(getDataSource());
    }

    private Map<String, MongoClient> mongoClientCache = new HashMap<>();


    private MongoClient getMongoClient() {
        String mongoUrl = props.property("mongo.url").orElse("mongodb://localhost:27017");
        return mongoClientCache.computeIfAbsent(mongoUrl, url -> new MongoClient(new MongoClientURI(url)));
    }

    private DataSource getDataSource() {
        return props.getDataSource("demo");
    }

}
