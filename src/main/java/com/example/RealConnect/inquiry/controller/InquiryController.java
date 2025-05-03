package com.example.RealConnect.inquiry.controller;

import com.example.RealConnect.inquiry.domain.dto.InquiryCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryResponseDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryUpdateRequest;
import com.example.RealConnect.inquiry.service.InquiryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.RealConnect.security.JwtTokenProvider;

import java.nio.file.AccessDeniedException;
import java.util.List;

// 프론트에서의 요청 수신, DTO매핑, 응답 전송
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService; //
    private final JwtTokenProvider jwtTokenProvider; //

    /*
    문의 등록
    1. @param dto: 프론트에서 입력한 문의 정보를 담고 있음
    2. @param request: request http 요청 객체, 요청 헤더에서 JWT토큰을 뽑아내기 위한 도구 / 공인중개사(사용자) 식별을 위함.
    == 프론트에서 요청 헤더에 jwt를 실어서 보내는데, 서버에서는 jwt 문자열만 추출.
    3. @return: 등록이 완료된 문의 정보를 담아 응답으로 보내줌
    */
    @PostMapping
    public ResponseEntity<InquiryResponseDto> registerInquiry(
            @RequestBody InquiryCreateRequestDto dto,
            HttpServletRequest request
    ) {
        // 요청 헤더에서 JWT 추출 → 사용자 ID 추출
        //String token = jwtTokenProvider.resolveToken(request); // security 안에서 jwttokenprovider 클래스 만들기
        //Long agentId = jwtTokenProvider.getUserId(token); // 수정 해야함

        Long agentId = 1L; // 테스트 용

        // 서비스 호출
        InquiryResponseDto response = inquiryService.register(dto, agentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    /*
    문의 조회(검색) - 조건 별 검색
    1. 진행 상태 드롭박스로 검색(조회) - 진행 중 / 진행 완료 / 전체
    2. 문의 유형 드롭박스로 검색(조회) - 매매 / 전세 / 월세 / 전체
    3. 통합검색창에 검색어 입력해서 검색(조회) - 문의자 이름, 단지
    4. 진행 상태, 문의 유형 드롭박스에서 '전체'를 클릭하면 모든 진행상태 또는 모든 문의 유형을 포함한 리스트들이 조회된다.
    5. 검색어 입력, 또는 드롭박스로 검색한 후 다시 전체 문의를 보고 싶은 경우
        '전체' 버튼 클릭 시 검색 조건들이 초기화되고 문의 전체가 다시 나타난다.
     */

    @GetMapping
    public ResponseEntity<List<InquiryResponseDto>> searchInquiries(
            @RequestParam(required = false) String status, // 진행 상태 드롭박스
            @RequestParam(required = false) String inquiryType, // 문의 우형 드롭박스
            @RequestParam(required = false) String keyword, // 통합검색 시 검색어
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        Long agentId = jwtTokenProvider.getUserId(token);

        // 드롭박스에서 '전체'가 입력되면 조건 적용하지 않도록 null 처리
        String filterStatus = "전체".equalsIgnoreCase(status) ? null : status;
        String filterType = "전체".equalsIgnoreCase(inquiryType) ? null : inquiryType;
        String filterKeyword = (keyword == null || keyword.isBlank()) ? null : keyword;

        // 조건을 받아서 service에서 처리
        List<InquiryResponseDto> result = inquiryService.searchInquiries(agentId, filterStatus, filterType, filterKeyword);

        // 검색 결과를 json으로 응답 - 프론트에서 바로 리스트 보여줄 수 있게 함.
        return ResponseEntity.ok(result);
    }

    /*
    문의 수정
     */
    @PutMapping("/{inquiryId}")
    public ResponseEntity<InquiryResponseDto> updateInquiry(
            @PathVariable("inquiryId") Long inquiryId,
            @RequestBody InquiryUpdateRequest dto,
            HttpServletRequest request
    ) throws Exception{
        // 요청 헤더에서 JWT 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 토큰에서 사용자 ID 추출
        Long agentId = jwtTokenProvider.getUserId(token);

        // 수정 진행
        InquiryResponseDto updated = inquiryService.updateInquiry(inquiryId, dto, agentId);

        //
        return ResponseEntity.ok(updated);
    }

    /*
    문의 삭제
     */
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(
            @PathVariable Long inquiryId,
            HttpServletRequest request
    ) throws AccessDeniedException {
        String token = jwtTokenProvider.resolveToken(request);
        Long agentId = jwtTokenProvider.getUserId(token);

        inquiryService.deleteInquiry(inquiryId, agentId);

        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
