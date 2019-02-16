package com.delicate.springjdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class FooDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    public void insertData() {
        // insert data demo
        Arrays.asList('b', 'c').forEach(bar -> {
            jdbcTemplate.update("INSERT INTO FOO(BAR) VALUES (?)", bar);
        });

        HashMap<String, String> valueMap = new HashMap<>();
        valueMap.put("BAR", "Allen");
        Number id = simpleJdbcInsert.executeAndReturnKey(valueMap);
        log.info("ID of Allen: {}", id.longValue());
    }

    public void listData() {
        // query for a result
        log.info("Count: {}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO", Long.class));

        // query for a result list of a field in the table
        List<String> barList = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        barList.forEach(bar -> log.info("Bar: {}", bar));

        // query for an object list
        // 从数据库查询并获取对象列表，实现 RowMapper 的一个匿名内部类建立数据库查询结果和 Foo 间的映射信息
        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
                return Foo.builder()
                        .id(resultSet.getLong(1))
                        .bar(resultSet.getString(2))
                        .build();
            }
        });
        fooList.forEach(foo -> log.info("Foo: {}", foo));
    }
}
