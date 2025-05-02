package com.example.RealConnect.inquiry.controller;

import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.inquiry.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shares")
public class ShareController {

    private final ShareService shareService;

    @PostMapping
    public ResponseEntity<Void> share(@RequestBody InquiryPostCreateRequestDto requestDto, Principal principal)
    {
        //유효성검사 필요

        shareService.share(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }
}
