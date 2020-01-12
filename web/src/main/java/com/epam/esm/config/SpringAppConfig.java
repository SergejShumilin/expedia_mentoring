package com.epam.esm.config;

import com.epam.esm.dao.connection.ConnectionPool;
import com.epam.esm.dao.connection.ManagerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class SpringAppConfig {

    @Bean
    public DataSource dataSource() {
        return new ManagerDataSource(ConnectionPool.getInstance());
    }
}
