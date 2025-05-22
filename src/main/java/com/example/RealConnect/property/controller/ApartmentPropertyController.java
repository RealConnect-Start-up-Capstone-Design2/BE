package com.example.RealConnect.property.controller;

import com.example.RealConnect.property.domain.dto.ApartmentPropertyPage;
import com.example.RealConnect.property.service.ApartmentPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 매물관리메뉴 조회에서 사용되는 API
 */
@RestController
@RequiredArgsConstructor
public class ApartmentPropertyController {

    private final ApartmentPropertyService apartmentPropertyService;

    /**
     * 매물 관리 [전체]
     * 동 호수 기준으로 모든 아파트 반환
     * 내 매물이 있다면 아파트에 매물 정보를 붙여 반환
     * 없다면 매물은 null
     * page = 1, size = 1000 일 경우, 1001~2000까지 반환됨
     * page = 5, size = 500 일 경우, 2501~3000
     * @param page
     * @param size 없거나, 잘못된 값이 들어온 경우, 1000으로 설정
     * @param principal
     * @return
     */
    @GetMapping("/api/apartments-properties")
    public ResponseEntity<Page<ApartmentPropertyPage>> getApartmentsWithProperty(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue = "1000") int size,
            Principal principal)
    {

        Page<ApartmentPropertyPage> result =
                apartmentPropertyService.findAllApartmentWithProperty(principal.getName(), page, size);
        return ResponseEntity.ok(result);
    }

}
