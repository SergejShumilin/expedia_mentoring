package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TagDaoImp implements TagDao<Tag> {
    private final static String INSERT = "insert into tags (name) values(?)";
    private final static String DELETE = "DELETE FROM tags WHERE id = ?";
    private final static String FIND_ALL = "select * from tags";
    private final static String FIND_BY_ID = "select * from tags where id = ?";
    private final static String FIND_BY_NAME = "select * from tags where name = ?";
    private final static String IS_EXISTS = "SELECT EXISTS(SELECT id FROM tags WHERE id = ?)";
    private final static String IS_EXISTS_BY_NAME = "SELECT EXISTS(SELECT name FROM tags WHERE name = ?)";
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    public TagDaoImp(TagMapper tagMapper, DataSource dataSource) {
        this.tagMapper = tagMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Tag tag) {
        jdbcTemplate.update(INSERT, tag.getName());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL, tagMapper);
    }

    @Override
    public Tag findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, tagMapper);
    }

    @Override
    public Tag findByName(String name) {
        return jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[]{name}, tagMapper);
    }

    @Override
    public boolean isExist(int id){
        return jdbcTemplate.queryForObject(IS_EXISTS, Boolean.class, id);
    }

    @Override
    public boolean isExistByName(String name){
        return jdbcTemplate.queryForObject(IS_EXISTS_BY_NAME, Boolean.class, name);
    }
}
