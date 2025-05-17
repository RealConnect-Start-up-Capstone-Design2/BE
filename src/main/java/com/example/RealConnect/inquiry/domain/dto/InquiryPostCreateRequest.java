package com.example.RealConnect.inquiry.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryPostCreateRequest {

    private String l1; // 시
    private String l2; // 구
    private String l3; // 동
    private String agentPhone; // 중개사 전화번호
    private String title; // 공유글 제목
    private String memo; //
}
