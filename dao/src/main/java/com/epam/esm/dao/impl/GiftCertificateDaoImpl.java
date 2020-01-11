package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificatesDao<GiftCertificate> {
    private final GiftCertificateMapper giftCertificateMapper;
    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(GiftCertificateMapper giftCertificateMapper, DataSource dataSource) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        jdbcTemplate.update("INSERT INTO certificates (name, description, price, create_date, last_update_date, duration, tag_id) VALUES (?,?,?,?,?,?,?)",
                        giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                        giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(), giftCertificate.getDuration(), giftCertificate.getTag().getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM certificates WHERE id = ?", id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query("SELECT * from certificates", giftCertificateMapper);
    }

    public GiftCertificate findById(int id) {
        return jdbcTemplate.queryForObject("select * from certificates where id = ?", new Object[]{id}, giftCertificateMapper);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update("UPDATE certificates SET name = ?, description=?, price=?, create_date=?, last_update_date=?, duration=?, tag_id =? WHERE id = ?",
                giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(), giftCertificate.getDuration(),giftCertificate.getTag().getId(), giftCertificate.getId());
    }
}
