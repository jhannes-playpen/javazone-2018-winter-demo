package com.soprasteria.johannes.winter.demo.person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.soprasteria.johannes.winter.framework.ExceptionUtil;
import org.fluentjdbc.DatabaseRow;
import org.fluentjdbc.DatabaseTableWithTimestamps;

public class JdbcPersonRepository implements PersonRepository {

    private DatabaseTableWithTimestamps table;
    private DataSource dataSource;

    public JdbcPersonRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        table = new DatabaseTableWithTimestamps("person");
    }

    @Override
    public List<Person> listAll() {
        try (Connection conn = dataSource.getConnection()) {
            return table.listObjects(conn, this::mapRowToPerson);
        } catch (SQLException e) {
            throw ExceptionUtil.soften(e);
        }
    }

    private Person mapRowToPerson(DatabaseRow r) throws SQLException {
        Person person = new Person();
        person.setId(r.getUUID("id"));
        person.setFamilyName(r.getString("familyName"));
        person.setGivenName(r.getString("givenName"));
        return person;
    }

    @Override
    public Person create(Person person) {
        try (Connection conn = dataSource.getConnection()) {
            person.setId(UUID.randomUUID());
            table.insert()
                .setPrimaryKey("id", person.getId())
                .setField("familyName", person.getFamilyName())
                .setField("givenName", person.getGivenName())
                .execute(conn);
            return person;
        } catch (SQLException e) {
            throw ExceptionUtil.soften(e);
        }
    }

}
