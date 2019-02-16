package com.delicate.springjdbcdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BatchFooDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchInsert() {
        jdbcTemplate.batchUpdate("INSERT INTO FOO(BAR) VALUES(?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, "BAR-" + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return 3;
                    }
                });

        List<Foo> foos = new ArrayList<>();
        foos.add(Foo.builder().id(100L).bar("BAR-100").build());
        foos.add(Foo.builder().id(101L).bar("BAR-101").build());
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO FOO(ID, BAR) VALUES(:id, :bar)",
                SqlParameterSourceUtils.createBatch(foos));
    }
}
