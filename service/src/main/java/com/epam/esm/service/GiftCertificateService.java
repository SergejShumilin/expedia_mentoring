package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class GiftCertificateService {
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); // Quoted "Z" to indicate UTC, no timezone offset
    private final GiftCertificatesDao<GiftCertificate> giftCertificatesDao;
    private final TagService tagService;

    public GiftCertificateService(GiftCertificatesDao<GiftCertificate> giftCertificatesDao, TagService tagService) {
        this.giftCertificatesDao = giftCertificatesDao;
        this.tagService = tagService;
    }

    public List<GiftCertificate> findAll() {
        return giftCertificatesDao.findAll();
    }

    public GiftCertificate findById(int id) throws CertificateNotFoundException {
        boolean exist = giftCertificatesDao.isExist(id);
        if (!exist) {
            throw new CertificateNotFoundException(id);
        }
        return giftCertificatesDao.findById(id);
    }

    public List<GiftCertificate> findByName(String name) throws CertificateNotFoundException {
        boolean exist = giftCertificatesDao.isExistByName(name);
        if (!exist) {
            throw new CertificateNotFoundException(name);
        }
        return giftCertificatesDao.findByName(name);
    }

    public void save(GiftCertificate giftCertificate) {
        checkAndSaveTagIfNotExist(giftCertificate);
        giftCertificate.setCreateDate(DATE_FORMAT.format(new Date()));
        giftCertificate.setLastUpdateDate(DATE_FORMAT.format(new Date()));
        giftCertificatesDao.save(giftCertificate);

        giftCertificatesDao.saveCon(giftCertificate.getId(), giftCertificate.getTag().getId());
    }

    public void update(GiftCertificate giftCertificate) throws CertificateNotFoundException {
        checkAndSaveTagIfNotExist(giftCertificate);
        GiftCertificate certificateFromDb = findById(giftCertificate.getId());
        boolean equalsCertificates = certificateFromDb.equals(giftCertificate);
        if (!equalsCertificates) {
            changeCertificate(certificateFromDb, giftCertificate);
            giftCertificatesDao.update(certificateFromDb);
        }
    }

    public void delete(int id) {
        giftCertificatesDao.delete(id);
    }

    public List<GiftCertificate> sortByDate(String typeSort) {
        return giftCertificatesDao.sort(typeSort);
    }

    private void checkAndSaveTagIfNotExist(GiftCertificate giftCertificate){
        Tag tag = giftCertificate.getTag();
        if (!tagService.isExistByName(tag.getName())) {
            tagService.save(tag);
        }
        tag = tagService.findByName(tag.getName());
        giftCertificate.setTag(tag);
    }

    private void changeCertificate(GiftCertificate certificateFromDb, GiftCertificate certificate) {
        if (!certificate.getName().equals(certificateFromDb.getName())) {
            certificateFromDb.setName(certificate.getName());
        }
        if (certificate.getPrice() != certificateFromDb.getPrice()) {
            certificateFromDb.setPrice(certificate.getPrice());
        }
        if (!certificate.getDescription().equals(certificateFromDb.getDescription())) {
            certificateFromDb.setDescription(certificate.getDescription());
        }
        if (!certificate.getTag().equals(certificateFromDb.getTag())) {
            certificateFromDb.setTag(certificate.getTag());
        }
        if (certificate.getDuration() != certificateFromDb.getDuration()) {
            certificateFromDb.setDuration(certificate.getDuration());
        }
        certificateFromDb.setLastUpdateDate(DATE_FORMAT.format(new Date()));
    }
}
