package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesDao;
import com.epam.esm.dao.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateService {

    private final GiftCertificatesDao<GiftCertificate> giftCertificatesDao;

    public GiftCertificateService(GiftCertificatesDao<GiftCertificate> giftCertificatesDao) {
        this.giftCertificatesDao = giftCertificatesDao;
    }

    public void save(GiftCertificate giftCertificate) {
        giftCertificatesDao.save(giftCertificate);
    }

    public void delete(GiftCertificate giftCertificate) {
        giftCertificatesDao.delete(giftCertificate);
    }

    public List<GiftCertificate> getAll() {
        return giftCertificatesDao.findAll();
    }

    public GiftCertificate findById(int id){
        return giftCertificatesDao.findById(id);
    }

    public void update(GiftCertificate giftCertificate) {
        giftCertificatesDao.update(giftCertificate);
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
