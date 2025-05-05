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
     * @return HTTP 200 OK 응답 (본문 없음)
     */
    @PostMapping("/api/shares")
    public ResponseEntity<InquiryPostResponseDto> share(@RequestBody InquiryPostCreateRequestDto requestDto, Principal principal)
    {
        //유효성검사 필요

        InquiryPostResponseDto post = shareService.share(principal.getName(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

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
