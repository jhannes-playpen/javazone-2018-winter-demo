package com.soprasteria.johannes.winter.demo.cats;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.soprasteria.johannes.winter.framework.ExceptionUtil;
import org.fluentjdbc.DatabaseRow;
import org.fluentjdbc.DatabaseTableWithTimestamps;

public class JdbcCatRepository implements CatRepository {

    private DataSource dataSource;

    private DatabaseTableWithTimestamps table = new DatabaseTableWithTimestamps("cats");

    public JdbcCatRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Cat> listAll() {
        try (Connection conn = dataSource.getConnection()) {
            return table.listObjects(conn, row -> mapToCat(row));
        } catch (SQLException ex) {
            throw ExceptionUtil.soften(ex);
        }
    }

    private Cat mapToCat(DatabaseRow row) throws SQLException {
        Cat cat = new Cat();
        cat.setId(row.getString("id"));
        cat.setName(row.getString("name"));
        // TODO: Upstream DatabaseRow#getLocalDate
        Date date = (Date)row.getObject("date_of_birth");
        cat.setDateOfBirth(date != null ? date.toLocalDate() : null);
        return cat;
    }

    @Override
    public Cat retrieve(String id) {
        try (Connection conn = dataSource.getConnection()) {
            return table.where("id", id).singleObject(conn, row -> mapToCat(row));
        } catch (SQLException ex) {
            throw ExceptionUtil.soften(ex);
        }
    }

    @Override
    public void create(Cat cat) {
        try (Connection conn = dataSource.getConnection()) {
            if (cat.getId() == null) {
                cat.setId(UUID.randomUUID().toString());
            }
            table.insert()
                .setPrimaryKey("id", cat.getId())
                .setField("name", cat.getName())
                .setField("date_of_birth", cat.getDateOfBirth())
                .execute(conn);
        } catch (SQLException ex) {
            throw ExceptionUtil.soften(ex);
        }

    }

}
