package com.example.RealConnect.contract.controller;

import com.example.RealConnect.contract.Exception.ContractNotFoundException;
import com.example.RealConnect.contract.Exception.ContractNotMatchException;
import com.example.RealConnect.contract.Exception.InvalidContractTypeException;
import com.example.RealConnect.contract.domain.ContractPostRequestDto;
import com.example.RealConnect.contract.domain.ContractResponseDto;
import com.example.RealConnect.contract.service.ContractService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // 계약등록
    @PostMapping("/api/contract")
    public ResponseEntity<?> registerDirectContract(@RequestBody ContractPostRequestDto dto, Principal principal) {
        ContractResponseDto responseDto = contractService.createContract(dto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 계약 조회의 종류 - 전체 / 즐겨찾기 / 계약 상태 / 통합 검색
    // 계약 전체 조회
    @GetMapping("/api/contract/searchContracts")
    public ResponseEntity<List<ContractResponseDto>> getContracts(
            @RequestParam(required = false) Boolean favorite, // 즐겨찾기
            @RequestParam(required = false) String type, //거래 유형 - 드롭박스
            @RequestParam(required = false) String keyword, // 통합검색 - 단지명, 임차인 이름 기준 검색
            Principal principal
    ) {
        List<ContractResponseDto> contracts = contractService.searchContracts(favorite, type, keyword,
                principal.getName());
        return ResponseEntity.ok(contracts);
    }

    // 계약 수정
    @PutMapping("/api/contract/update/{contractId}")
    public ResponseEntity<?> updateContract(
            @PathVariable Long contractId,
            @RequestBody ContractPostRequestDto dto,
            Principal principal // 현재 로그인된 사용자
    ) {
        ContractResponseDto responseDto = contractService.updateContract(contractId, dto, principal.getName());// 계약 존재 여부 및 권한 검증 후 계약 수정
        return ResponseEntity.ok(responseDto);
    }

    // 계약삭제
    @DeleteMapping("/api/contract/delete/{contractId}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long contractId, Principal principal) {
        contractService.deleteContract(contractId, principal.getName());  // 계약 삭제 처리
        return ResponseEntity.noContent().build();  // 성공적으로 삭제되면 204 No Content 반환
    }

    // 예외처리
    @ExceptionHandler(InvalidContractTypeException.class)
    public ResponseEntity<String> handleInvalidContractTypeException(InvalidContractTypeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * 존재하지 않는 계약에 대한 요청
     * @param e
     * @return 404
     */
    @ExceptionHandler({ContractNotFoundException.class})
    public ResponseEntity<String> handleContractNotFoundException(ContractNotFoundException e)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * 계약 권한 없는 경우
     * @param e
     * @return 403
     */
    @ExceptionHandler({ContractNotMatchException.class})
    public ResponseEntity<String> handleContractNotMatchException(ContractNotMatchException e)
    {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

}
