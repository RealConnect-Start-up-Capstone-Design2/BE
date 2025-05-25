package com.example.RealConnect.inquiry.domain.dto;

import com.example.RealConnect.inquiry.domain.Inquiry;
import com.example.RealConnect.inquiry.domain.InquiryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 문의 등록 후, 조회 시 시용자에게 보여줄 정보 담음
@Getter
@Builder
public class InquiryResponseDto {

    private Long id; // 문의 식별용 id
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
    private InquiryStatus status; // 진행 상태
    private LocalDateTime createdAt; // 등록일

    // Entity → DTO 변환용 정적 메서드
    public static InquiryResponseDto from(Inquiry inquiry) {
        return InquiryResponseDto.builder()
                .id(inquiry.getId())
                .name(inquiry.getName())
                .phone(inquiry.getPhone())
                .inquiryType(String.valueOf(inquiry.getType()))
                .apartmentName(inquiry.getApartmentName())
                .area(Double.parseDouble(inquiry.getArea()))
                .salePrice(inquiry.getSalePrice())
                .jeonsePrice(inquiry.getJeonsePrice())
                .deposit(inquiry.getDeposit())
                .monthPrice(inquiry.getMonthPrice())
                .memo(inquiry.getMemo())
                .status(inquiry.getStatus())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }
}
