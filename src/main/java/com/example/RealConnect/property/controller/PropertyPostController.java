package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.exception.ApartmentNotMatchException;
import com.example.RealConnect.property.service.PropertyPostService;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.exception.PasswordNotMatchException;
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

    @PostMapping("api/properties/add")
    public ResponseEntity<String> saveProperty(@RequestBody @Valid PropertyRequestDto propertyRequestDto, Principal principal){
        propertyPostService.save(propertyRequestDto, principal.getName());
        return ResponseEntity.ok("저장 성공!");
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
