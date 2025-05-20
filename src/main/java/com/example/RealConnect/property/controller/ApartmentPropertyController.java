package com.example.RealConnect.property.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 매물관리메뉴 조회에서 사용되는 API
 */
@RestController
@RequiredArgsConstructor
public class ApartmentPropertyController {

    @GetMapping("/api/apartments-properties/{page}")
    public ResponseEntity<> getApartmentsWithProperty(@PathVariable Long page, Principal principal)
    {

    }

}
