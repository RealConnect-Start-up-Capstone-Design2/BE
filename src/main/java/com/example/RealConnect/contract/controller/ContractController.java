package com.example.RealConnect.contract.controller;

import com.example.RealConnect.contract.domain.ContractPostRequestDto;
import com.example.RealConnect.contract.domain.ContractResponseDto;
import com.example.RealConnect.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // 계약 등록 - ContractPostResponseDto 받아서 처리
    // propertyID, inquiryID를 통해 Property, Inquiry 엔티티 조회해서 Contract 생성 후 저장
    // requestbody - json으로 전달되는 데이터를 dto로 바인딩
    @PostMapping
    public ResponseEntity<Void> registerContract(@RequestBody ContractPostRequestDto dto) {
        contractService.registerContract(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // 계약 조회의 종류 - 전체 / 즐겨찾기 / 계약 상태 / 통합 검색
    // 계약 전체 조회
    @GetMapping
    public ResponseEntity<List<ContractResponseDto>> getContracts(
            @RequestParam(required = false) Boolean favorite, // 즐겨찾기
            @RequestParam(required = false) String type, //거래 유형 - 드롭박스
            @RequestParam(required = false) String keyword // 통합검색 - 단지명, 임차인 이름 기준 검색
    ) {
        List<ContractResponseDto> contracts = contractService.searchContracts(favorite, type, keyword);
        return ResponseEntity.ok(contracts);
    }

}
