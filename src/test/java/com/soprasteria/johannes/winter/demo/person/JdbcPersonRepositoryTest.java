package com.soprasteria.johannes.winter.demo.person;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;

public class JdbcPersonRepositoryTest extends PersonRepositoryTest {

    private JdbcPersonRepository personRepository;


    @Before
    public void setupRepository() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:personRepository;DB_CLOSE_DELAY=-1");

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();

        personRepository = new JdbcPersonRepository(dataSource);
    }


    @Override
    protected PersonRepository getPersonRepository() {
        return personRepository;
    }

}
