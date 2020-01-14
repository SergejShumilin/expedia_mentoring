package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificatesDao<GiftCertificate> {
    private final static String INSERT = "INSERT INTO certificates (name, description, price, create_date, last_update_date, duration) VALUES (?,?,?,?,?,?)";
//    private final static String INSERT = "INSERT INTO certificates (name, description, price, create_date, last_update_date, duration, tag_id) VALUES (?,?,?,?,?,?,?)";
    private final static String DELETE = "DELETE FROM certificates WHERE id = ?";
    private final static String FIND_ALL = "select certificates.id, certificates.name, certificates.description, certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration, tags.id as tag_id, tags.name as tag_name from certificates inner join tags on certificates.tag_id=tags.id";
    private final static String FIND_BY_ID = "select certificates.id, certificates.name, certificates.description, certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration, tags.id as tag_id, tags.name as tag_name from certificates inner join tags on certificates.tag_id=tags.id where certificates.id = ?";
    private final static String FIND_BY_NAME = "select certificates.id, certificates.name, certificates.description, certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration, tags.id as tag_id, tags.name as tag_name from certificates inner join tags on certificates.tag_id=tags.id where certificates.name like ?";
    private final static String UPDATE = "UPDATE certificates SET name = ?, description=?, price=?, create_date=?, last_update_date=?, duration=?, tag_id =? WHERE id = ?";
    private final static String IS_EXISTS = "SELECT EXISTS(SELECT id FROM certificates WHERE id = ?)";
    private final static String IS_EXISTS_BY_NAME = "SELECT EXISTS(SELECT name FROM certificates WHERE name like ?)";
    private final static String SORT_BY_DATE_DESC = "select certificates.id, certificates.name, certificates.description, certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration, tags.id as tag_id, tags.name as tag_name from certificates inner join tags on certificates.tag_id=tags.id ORDER BY create_date DESC";
    private final static String SORT_BY_DATE_ASC = "select certificates.id, certificates.name, certificates.description, certificates.price, certificates.create_date, certificates.last_update_date, certificates.duration, tags.id as tag_id, tags.name as tag_name from certificates inner join tags on certificates.tag_id=tags.id ORDER BY create_date";
    private final GiftCertificateMapper giftCertificateMapper;
    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(GiftCertificateMapper giftCertificateMapper, DataSource dataSource) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        jdbcTemplate.update(INSERT,giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration());
    }

    public void saveCon(int id, int tagId){
        jdbcTemplate.update("INSERT INTO connecting (certificate_id, tag_id) values (?,?)", id, tagId);
    }

//    @Override
//    public void save(GiftCertificate giftCertificate) {
//        jdbcTemplate.update(INSERT,giftCertificate.getName(),
//                giftCertificate.getDescription(), giftCertificate.getPrice(),
//                giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(),
//                giftCertificate.getDuration(), giftCertificate.getTag().getId());
//    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL, giftCertificateMapper);
    }

    @Override
    public GiftCertificate findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, giftCertificateMapper);
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, new Object[]{"%"+name+"%"}, giftCertificateMapper);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration(),giftCertificate.getTag().getId(), giftCertificate.getId());
    }

    @Override
    public boolean isExist(int id){
        return jdbcTemplate.queryForObject(IS_EXISTS, Boolean.class, id);
    }

    @Override
    public boolean isExistByName(String name){
        return jdbcTemplate.queryForObject(IS_EXISTS_BY_NAME, Boolean.class, "%"+name+"%");
    }

    @Override
    public List<GiftCertificate> sort(String type){
        String query = SORT_BY_DATE_ASC;
        if (type.equalsIgnoreCase("desc")){
            query = SORT_BY_DATE_DESC;
        }
        return jdbcTemplate.query(query, giftCertificateMapper);
    }
}
