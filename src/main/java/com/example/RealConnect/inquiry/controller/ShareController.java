package com.example.RealConnect.inquiry.controller;

import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostResponseDto;
import com.example.RealConnect.inquiry.service.ShareService;
import lombok.RequiredArgsConstructor;
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
     * @param requestDto
     * @param principal
     * @return
     */
    @PostMapping("/api/shares")
    public ResponseEntity<Void> share(@RequestBody InquiryPostCreateRequestDto requestDto, Principal principal)
    {
        //유효성검사 필요

        shareService.share(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 내 공유글 조회
     */
    @GetMapping("/api/shares/my")
    public ResponseEntity<List<InquiryPostResponseDto>> myShares(Principal principal)
    {
        List<InquiryPostResponseDto> myPosts = shareService.myInquiryPosts(principal.getName());
        return ResponseEntity.ok(myPosts);
    }

}
