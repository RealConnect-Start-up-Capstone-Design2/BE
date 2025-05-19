package com.example.RealConnect.inquiry.domain.dto;

// 프론트에서 문의 등록 정보를 보낼 떄 사용하는 데이터 형식
// 사용자가 입력하는 값만 담음.

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryCreateRequestDto {

    private String name; // 문의자 이름
    private String phone; // 문의자 연락처
    private String inquiryType; // 문의 유형
    private String apartmentName; // 단지
    private double area; // 면적
    private Long salePrice; // 매매가
    private Long jeonsePrice; // 전세가
    private Long deposit; // 보증금
    private Long monthPrice; // 월세
    private String memo; // 문의 내용

}
