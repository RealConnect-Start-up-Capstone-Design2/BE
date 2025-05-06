package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyStatusDto;
import com.example.RealConnect.property.service.PropertyModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PropertyModifyController {

    private final PropertyModifyService propertyModifyService;

    // 매물 정보 수정
    @PutMapping("/api/properties/{aId}")
    public ResponseEntity<String> modify(@PathVariable("aId") Long id, @RequestBody PropertyRequestDto propertyRequestDto){
        propertyModifyService.modify(id, propertyRequestDto);
        return ResponseEntity.ok("매물 정보 수정 성공!");
    }

    // 매물 상태 변경
    @PatchMapping("api/property/status/{aId}")
    public ResponseEntity<String> modifyStatus(@PathVariable("aId") Long id, @RequestBody PropertyStatusDto propertyStatusDto){
        propertyModifyService.changeStatus(id, propertyStatusDto);
        return ResponseEntity.ok("상태 수정 완료!");
    }
}
