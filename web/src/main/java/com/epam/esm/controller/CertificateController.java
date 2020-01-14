package com.epam.esm.controller;

import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.dao.entity.GiftCertificate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final GiftCertificateService giftCertificateService;

    public CertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> findAll() {
        return giftCertificateService.findAll();
    }

    @GetMapping(value = "/{name}")
    public List<GiftCertificate> findByName(@PathVariable String name) throws CertificateNotFoundException {
        return giftCertificateService.findByName(name);
    }

    @PostMapping
    public List<GiftCertificate> save(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.save(giftCertificate);
        return giftCertificateService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public List<GiftCertificate> delete(@PathVariable int id) {
        giftCertificateService.delete(id);
        return giftCertificateService.findAll();
    }

    @GetMapping(value = "/date_sort/{type}")
    public List<GiftCertificate> sort(@PathVariable String type) {
        return giftCertificateService.sortByDate(type);
    }

    @PutMapping
    public List<GiftCertificate> update(@RequestBody GiftCertificate giftCertificate) throws CertificateNotFoundException {
        giftCertificateService.update(giftCertificate);
        return giftCertificateService.findAll();
    }

}
