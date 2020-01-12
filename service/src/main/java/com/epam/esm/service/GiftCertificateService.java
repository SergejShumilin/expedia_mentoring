package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GiftCertificateService {

    private final GiftCertificatesDao<GiftCertificate> giftCertificatesDao;
    private final TagService tagService;

    public GiftCertificateService(GiftCertificatesDao<GiftCertificate> giftCertificatesDao, TagService tagService) {
        this.giftCertificatesDao = giftCertificatesDao;
        this.tagService = tagService;
    }

    private String getDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); // Quoted "Z" to indicate UTC, no timezone offset
        return df.format(new Date());
    }

    public void save(GiftCertificate giftCertificate) {
        Tag tag = giftCertificate.getTag();
        if (!tagService.isExist(tag.getId())){
            tagService.save(tag);
        }
        giftCertificate.setCreateDate(getDate());
        giftCertificate.setLastUpdateDate(getDate());
        giftCertificatesDao.save(giftCertificate);
    }

    public void delete(int id) {
        giftCertificatesDao.delete(id);
    }

    public List<GiftCertificate> getAll() {
        return giftCertificatesDao.findAll();
    }

    public GiftCertificate findById(int id) throws CertificateNotFoundException {
        boolean exist = giftCertificatesDao.isExist(id);
        if (!exist){ throw new CertificateNotFoundException(id);}
        return giftCertificatesDao.findById(id);
    }

    public void update(GiftCertificate giftCertificate) throws CertificateNotFoundException {
        GiftCertificate certificateFromDb = findById(giftCertificate.getId());
        boolean equalsCertificates = certificateFromDb.equals(giftCertificate);
        if (!equalsCertificates) {
            changeCertificate(certificateFromDb, giftCertificate);
            giftCertificatesDao.update(certificateFromDb);
        }
    }

    private void changeCertificate(GiftCertificate certificateFromDb, GiftCertificate certificate){
       if (!certificate.getName().equals(certificateFromDb.getName())){
           certificateFromDb.setName(certificate.getName());
       }
       if(certificate.getPrice()!=certificateFromDb.getPrice()){
           certificateFromDb.setPrice(certificate.getPrice());
       }
       if (!certificate.getDescription().equals(certificateFromDb.getDescription())){
           certificateFromDb.setDescription(certificate.getDescription());
       }
       if (!certificate.getTag().equals(certificateFromDb.getTag())){
           certificateFromDb.setTag(certificate.getTag());
       }
       if (certificate.getDuration()!=certificateFromDb.getDuration()){
           certificateFromDb.setDuration(certificate.getDuration());
       }
       certificateFromDb.setLastUpdateDate(getDate());
    }

    public List<GiftCertificate> sortByName(String typeSort) {
        List<GiftCertificate> giftCertificates = giftCertificatesDao.findAll();
        List<GiftCertificate> collect = new ArrayList<>();
        if (typeSort.equals("desc")) {
            collect = giftCertificates.stream()
                    .sorted(Comparator.comparing(GiftCertificate::getName).reversed())
                    .collect(Collectors.toList());
        } else if (typeSort.equals("asc")) {
            collect = giftCertificates.stream()
                    .sorted(Comparator.comparing(GiftCertificate::getName))
                    .collect(Collectors.toList());
        }
        return collect;
    }
}
