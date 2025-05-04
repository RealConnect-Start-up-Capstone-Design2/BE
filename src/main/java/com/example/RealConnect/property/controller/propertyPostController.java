package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.service.propertyPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class propertyPostController {

    private final propertyPostService propertyPostService;

    @PostMapping("api/properties/add")
    public ResponseEntity<String> saveProperty(@RequestBody @Valid PropertyRequestDto propertyRequestDto){
        propertyPostService.save(propertyRequestDto);
        return ResponseEntity.ok("저장 성공!");
    }
}
