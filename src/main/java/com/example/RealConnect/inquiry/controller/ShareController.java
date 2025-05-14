package com.example.RealConnect.inquiry.controller;

import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostResponseDto;
import com.example.RealConnect.inquiry.exception.AccessDeniedException;
import com.example.RealConnect.inquiry.exception.InquiryPostNotFoundException;
import com.example.RealConnect.inquiry.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    /**
     * 공유글 작성
     * @param requestDto 공유글 생성에 필요한 데이터가 담긴 DTO
     * @param principal 현재 인증된 사용자의 보안 컨텍스트 정보
     * @return 201 + 생성된 공유글 DTO
     */
    @PostMapping("/api/shares")
    public ResponseEntity<InquiryPostResponseDto> share(@RequestBody InquiryPostCreateRequestDto requestDto, Principal principal)
    {
        //유효성검사 필요

        InquiryPostResponseDto post = shareService.share(principal.getName(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    /**
     * 공유글 1개 조회
     * 본인이 작성한 글인 경우, 고객의 정보가 포함
     * 타인이 작성한 글인 경우, 고객의 정보가 null로 반환
     * @param id
     * @param principal
     * @return 200 + 조회한 공유글 DTO
     */
    @GetMapping("/api/shares/{id}")
    public ResponseEntity<InquiryPostResponseDto> getShare(@PathVariable Long id, Principal principal)
    {
        InquiryPostResponseDto post = shareService.getShare(principal.getName(), id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    /**
     * 공유글 삭제
     * @param id 공유글 ID
     * @param principal 현재 인증된 사용자의 보안 컨텍스트 정보
     * @return 204
     */
    @DeleteMapping("/api/shares/{id}")
    public ResponseEntity<Void> deleteShare(@PathVariable Long id, Principal principal)
    {
        shareService.delete(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }


    /**
     * 내 공유글 조회
     * @param principal 현재 인증된 사용자의 보안 컨텍스트 정보
     * @return 요청자가 작성한 공유글 목록을 담은 DTO 리스트 (empty list일 수도 있음)
     */
    @GetMapping("/api/shares/my")
    public ResponseEntity<List<InquiryPostResponseDto>> myShares(Principal principal)
    {
        List<InquiryPostResponseDto> myPosts = shareService.myInquiryPosts(principal.getName());
        return ResponseEntity.ok(myPosts);
    }

    /**
     * 공유글 수정(전체 필드를 덮어씀)
     * @param id 공유글 ID
     * @param requestDto 공유글 수정에 필요한 데이터가 담긴 DTO
     * @param principal 현재 인증된 사용자의 보안 컨텍스트 정보
     * @return 200 + 수정된 공유글 DTO
     */
    @PutMapping("/api/shares/{id}")
    public ResponseEntity<InquiryPostResponseDto> modifyShare(@PathVariable Long id, @RequestBody InquiryPostCreateRequestDto requestDto, Principal principal)
    {
        InquiryPostResponseDto post = shareService.modify(principal.getName(), id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }


    /**
     * 로그인한 사용자가 공유글을 지역 조건(시·구·동)으로 검색
     * @param l1 시
     * @param l2 구
     * @param l3 동
     * @param principal
     * @return 조건에 맞는 공유글 리스트
     */
    @GetMapping("/api/shares")
    public ResponseEntity<List<InquiryPostResponseDto>> searchInquiryPost(
            @RequestParam(required = false, defaultValue = "") String l1,
            @RequestParam(required = false, defaultValue = "") String l2,
            @RequestParam(required = false, defaultValue = "") String l3,
            Principal principal)
    {
        List<InquiryPostResponseDto> inquiryPostResponseDtos = shareService.searchInquiryPosts(principal.getName(), l1, l2, l3);
        return ResponseEntity.ok(inquiryPostResponseDtos);
    }




    /**
     * 요청한 공유글이 존재하지 않는 경우
     * @param e
     * @return 404
     */
    @ExceptionHandler(InquiryPostNotFoundException.class)
    public ResponseEntity<String> handleInquiryPostNotFound(InquiryPostNotFoundException e)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * 공유글에 대한 권한이 없는 경우
     * @param e
     * @return 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleForbidden(AccessDeniedException e)
    {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
