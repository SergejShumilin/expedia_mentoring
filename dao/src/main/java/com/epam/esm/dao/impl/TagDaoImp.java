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
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    public TagDaoImp(TagMapper tagMapper, DataSource dataSource) {
        this.tagMapper = tagMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Tag tag) {
        jdbcTemplate.update("insert into tag values(?, ?)", tag.getId(), tag.getName());
    }

    @Override
    public void delete(Tag tag) {
        jdbcTemplate.update("DELETE FROM tag WHERE id = ?", tag.getId());
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("select * from tag", tagMapper);
    }

    public Tag findById(int id) {
        return jdbcTemplate.queryForObject("select * from tag where id = ?", new Object[]{id}, tagMapper);
    }
}
