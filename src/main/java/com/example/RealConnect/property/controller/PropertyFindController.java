package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.service.PropertyFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PropertyFindController {
    private final PropertyFindService propertyFindService;

    // 모든 매물
    @GetMapping("/api/properties/find/all")
    public ResponseEntity<List<PropertyResponseDto>> findAll() {
        return ResponseEntity.ok(propertyFindService.findAll());
    }



    // 내 매물
    @GetMapping("/api/properties/find/mine")
    public ResponseEntity<List<PropertyResponseDto>> findMine(Principal principal) {
        return ResponseEntity.ok(propertyFindService.findMine(principal.getName()));
    }
}
