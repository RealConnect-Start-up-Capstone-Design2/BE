package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.ApartmentPropertyDto;
import com.example.RealConnect.property.domain.dto.PropertyCreateRequestDto;
import com.example.RealConnect.property.exception.ApartmentNotFoundException;
import com.example.RealConnect.property.exception.PropertyAlreadyExistsException;
import com.example.RealConnect.property.service.PropertyPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /**
     *
     * @param propertyCreateRequestDto
     * @param principal
     * @return
     */
    @PostMapping("api/properties")
    public ResponseEntity<ApartmentPropertyDto> saveProperty(@RequestBody @Valid PropertyCreateRequestDto propertyCreateRequestDto, Principal principal){
        ApartmentPropertyDto dto = propertyPostService.save(propertyCreateRequestDto, principal.getName());
        return ResponseEntity.ok(dto);
    }

    /**
     * 아파트가 없을 경우
     * @param error
     * @return 400
     */
    @ExceptionHandler({ApartmentNotFoundException.class})
    public ResponseEntity<String> handleApartmentNotFind(ApartmentNotFoundException error) {
        return ResponseEntity
                .badRequest()
                .body(error.getMessage());
    }

    /**
     * 중복된 매물 생성 요청
     * @param error
     * @return 409
     */
    @ExceptionHandler({PropertyAlreadyExistsException.class})
    public ResponseEntity<String> handlePropertyAlreadyExists(PropertyAlreadyExistsException error)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error.getMessage());
    }

}
