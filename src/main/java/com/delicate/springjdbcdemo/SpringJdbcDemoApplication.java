package com.delicate.springjdbcdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SpringBootApplication
public class SpringJdbcDemoApplication implements CommandLineRunner {
    @Autowired
    private FooDAO fooDAO;

    @Autowired
    private BatchFooDAO batchFooDAO;

    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcDemoApplication.class, args);
    }

    @Bean
    @Autowired
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("FOO").usingGeneratedKeyColumns("ID");
    }

    @Override
    public void run(String... args) throws Exception {
        fooDAO.insertData();
        batchFooDAO.batchInsert();
        fooDAO.listData();
    }
}

