package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.domain.dto.PropertyStatusDto;
import com.example.RealConnect.property.exception.ApartmentNotMatchException;
import com.example.RealConnect.property.exception.PropertyNotMatchException;
import com.example.RealConnect.property.service.PropertyModifyService;
import com.example.RealConnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PropertyModifyController {

    private final PropertyModifyService propertyModifyService;

    // 매물 정보 수정
    @PutMapping("/api/properties/{aId}")
    public ResponseEntity<PropertyResponseDto> modify(@PathVariable("aId") Long id, @RequestBody PropertyRequestDto propertyRequestDto, Principal principal) {
        User agent = propertyModifyService.findUser(principal.getName());
        PropertyResponseDto responseDto = propertyModifyService.modify(id, propertyRequestDto, agent);
        return ResponseEntity.ok(responseDto);
    }

    // 매물 상태 변경
    @PatchMapping("api/property/status/{aId}")
    public ResponseEntity<String> modifyStatus(@PathVariable("aId") Long id, @RequestBody PropertyStatusDto propertyStatusDto){
        propertyModifyService.changeStatus(id, propertyStatusDto);
        return ResponseEntity.ok("상태 수정 완료!");
    }

    /**
     * 매물이 없을 경우
     * @param error
     * @return 400
     */
    @ExceptionHandler({PropertyNotMatchException.class})
    public ResponseEntity<String> handlePropertyNotFind(PropertyNotMatchException error) {
        return ResponseEntity
                .badRequest()
                .body(error.getMessage());
    }

    /**
     * 아파트가 없을 경우
     * @param error
     * @return 409
     */
    @ExceptionHandler({ApartmentNotMatchException.class})
    public ResponseEntity<String> handleApartmentNotFind(ApartmentNotMatchException error) {
        return ResponseEntity
                .badRequest()
                .body(error.getMessage());
    }
}
