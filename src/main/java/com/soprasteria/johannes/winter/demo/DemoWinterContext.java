package com.soprasteria.johannes.winter.demo;

import javax.sql.DataSource;

import com.soprasteria.johannes.winter.demo.person.JdbcPersonRepository;
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
            return null;
        } else {
            return getJdbcPersonRepository();
        }
    }

    private PersonRepository getJdbcPersonRepository() {
        return new JdbcPersonRepository(getDataSource());
    }

    private DataSource getDataSource() {
        return props.getDataSource("demo");
    }

}
