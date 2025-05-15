package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.exception.ApartmentNotMatchException;
import com.example.RealConnect.property.service.PropertyPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PropertyPostController {

    private final PropertyPostService propertyPostService;

    @PostMapping("api/properties")
    public ResponseEntity<PropertyResponseDto> saveProperty(@RequestBody @Valid PropertyRequestDto propertyRequestDto, Principal principal){
        PropertyResponseDto dto = propertyPostService.save(propertyRequestDto, principal.getName());
        return ResponseEntity.ok(dto);
    }

    /**
     * 아파트가 없을 경우
     * @param error
     * @return 400
     */
    @ExceptionHandler({ApartmentNotMatchException.class})
    public ResponseEntity<String> handleApartmentNotFind(ApartmentNotMatchException error) {
        return ResponseEntity
                .badRequest()
                .body(error.getMessage());
    }
}
