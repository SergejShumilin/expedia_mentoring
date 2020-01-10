package com.epam.esm.controller;

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

    @PostMapping
    public List<GiftCertificate> save(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.save(giftCertificate);
        return giftCertificateService.getAll();
    }

    @GetMapping
    public List<GiftCertificate> getAllCertificates() {
        return giftCertificateService.getAll();
    }

    @GetMapping(value = "/{id}")
    public GiftCertificate findById(@PathVariable int id) {
        return giftCertificateService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public List<GiftCertificate> delete(@PathVariable int id) {
        GiftCertificate giftCertificate = giftCertificateService.findById(id);
        giftCertificateService.delete(giftCertificate);
        return giftCertificateService.getAll();
    }

    @GetMapping(value = "/sort/{type}")
    public List<GiftCertificate> sort(@PathVariable String type) {
        return giftCertificateService.sortByName(type);
    }

}
