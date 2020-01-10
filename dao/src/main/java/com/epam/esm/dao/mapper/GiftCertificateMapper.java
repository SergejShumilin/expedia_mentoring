package com.epam.esm.dao.mapper;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.impl.TagDaoImp;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private final TagDaoImp tagDaoImp;

    public GiftCertificateMapper(TagDaoImp tagDaoImp) {
        this.tagDaoImp = tagDaoImp;
    }

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getInt("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getInt("price"));
        giftCertificate.setCreateDate(rs.getString("create_date"));
        giftCertificate.setLastUpdateDate(rs.getString("last_update_date"));
        giftCertificate.setDuration(rs.getInt("duration"));

        Tag tag_id = tagDaoImp.findById(rs.getInt("tag_id"));
        giftCertificate.setTag(tag_id);
        return giftCertificate;
    }

}
