package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.PropertyCreateRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyModifyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.domain.dto.PropertyStatusDto;
import com.example.RealConnect.property.exception.ApartmentNotMatchException;
import com.example.RealConnect.property.exception.IllegalPropertyStatusException;
import com.example.RealConnect.property.exception.PropertyNotFoundException;
import com.example.RealConnect.property.exception.PropertyNotMatchException;
import com.example.RealConnect.property.service.PropertyModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PropertyModifyController {

    private final PropertyModifyService propertyModifyService;

    // 매물 정보 수정
    @PutMapping("/api/properties/{propertyId}")
    public ResponseEntity<PropertyResponseDto> modify(@PathVariable("propertyId") Long id,
                                                      @RequestBody PropertyModifyRequestDto propertyModifyRequestDto,
                                                      Principal principal)
    {
        PropertyResponseDto responseDto = propertyModifyService.modify(id, propertyModifyRequestDto, principal.getName());
        return ResponseEntity.ok(responseDto);
    }

    // 매물 상태 변경
    @PatchMapping("api/property/status/{propertyId}")
    public ResponseEntity<String> modifyStatus(@PathVariable("propertyId") Long id, @RequestBody PropertyStatusDto propertyStatusDto){
        propertyModifyService.changeStatus(id, propertyStatusDto);
        return ResponseEntity.ok("상태 수정 완료!");
    }

    /**
     * 매물 수정 권한이 없는 경우
     * @param error
     * @return 403
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PropertyNotMatchException.class)
    public String handlePropertyNotMatch(PropertyNotMatchException error) {
        return error.getMessage();
    }


    /**
     * 매물이 없는 경우
     * @param error
     * @return
     */
    @ExceptionHandler({PropertyNotFoundException.class})
    public ResponseEntity<String> handlePropertyNotFound(PropertyNotFoundException error)
    {
        return ResponseEntity
                .badRequest()
                .body(error.getMessage());
    }

    /**
     * 잘못된 상태 변경
     * @param error
     * @return
     */
    @ExceptionHandler({IllegalPropertyStatusException.class})
    public ResponseEntity<String> handlePropertyNotFound(IllegalPropertyStatusException error)
    {
        return ResponseEntity
                .badRequest()
                .body(error.getMessage());
    }
}
