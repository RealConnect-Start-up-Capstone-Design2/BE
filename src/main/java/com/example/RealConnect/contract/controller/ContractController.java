package com.example.RealConnect.contract.controller;

import com.example.RealConnect.contract.domain.ContractPostRequestDto;
import com.example.RealConnect.contract.domain.ContractResponseDto;
import com.example.RealConnect.contract.service.ContractService;
import com.example.RealConnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // 매물관리, 문의관리를 거치지 않고 바로 계약등록
    @PostMapping("/api/contract/registerDirect")
    public ResponseEntity<Void> registerDirectContract(@RequestBody ContractPostRequestDto dto, Principal principal) {
        contractService.registerDirectContract(dto, principal.getName());  // 직접 계약 등록
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // 1. 매물 관리에서 계약 등록 - ContractPostResponseDto 받아서 처리
    // requestbody - json으로 전달되는 데이터를 dto로 바인딩
    @PostMapping("/api/contract/registerFromProperty")
    public ResponseEntity<Void> registerContractFromProperty(
            @RequestBody ContractPostRequestDto dto,
            @AuthenticationPrincipal User user)
    {
        contractService.registerContractFromProperty(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 2. 문의 관리에서 계약 등록
    @PostMapping("/api/contract/registerFromInquiry")
    public ResponseEntity<Void> registerContractFromInquiry(
            @RequestBody ContractPostRequestDto dto,
            @AuthenticationPrincipal User user)
    {
        contractService.registerContractFromInquiry(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 계약 조회의 종류 - 전체 / 즐겨찾기 / 계약 상태 / 통합 검색
    // 계약 전체 조회
    @GetMapping("/api/contract/searchContracts")
    public ResponseEntity<List<ContractResponseDto>> getContracts(
            @RequestParam(required = false) Boolean favorite, // 즐겨찾기
            @RequestParam(required = false) String type, //거래 유형 - 드롭박스
            @RequestParam(required = false) String keyword // 통합검색 - 단지명, 임차인 이름 기준 검색
    ) {
        List<ContractResponseDto> contracts = contractService.searchContracts(favorite, type, keyword);
        return ResponseEntity.ok(contracts);
    }

    // 계약 수정
    @PutMapping("/api/contract/update/{contractId}")
    public ResponseEntity<Void> updateContract(
            @PathVariable Long contractId,
            @RequestBody ContractPostRequestDto dto,
            @AuthenticationPrincipal User user // 현재 로그인된 사용자
    ) {
        contractService.updateContract(contractId, dto, user);  // 계약 존재 여부 및 권한 검증 후 계약 수정
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content 반환
    }

    // 계약삭제
    @DeleteMapping("/api/contract/delete/{contractId}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);  // 계약 삭제 처리
        return ResponseEntity.noContent().build();  // 성공적으로 삭제되면 204 No Content 반환
    }

}
