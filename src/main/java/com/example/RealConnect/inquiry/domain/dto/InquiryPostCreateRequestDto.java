package com.example.RealConnect.inquiry.domain.dto;

import com.example.RealConnect.inquiry.domain.InquiryPost;
import com.example.RealConnect.inquiry.domain.InquiryStatus;
import com.example.RealConnect.inquiry.domain.InquiryType;
import com.example.RealConnect.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InquiryPostCreateRequestDto {

    /**
     * 작성자
     */
    private User agent;

    /**
     * 문의 공유 제목
     */
    @NotBlank
    private String title;

    /**
     * 문의 지역 구분: 시(도) | 구(시) | 동
     */
    private String l1; //경기도, 서울특별시 등등
    private String l2; //과천시, 강남구 등등
    private String l3; //별양동, ~동 등등

    /**
     * 업소명
     */
    @NotBlank
    private String agentName;

    /**
     * 업소 연락처
     */
    @NotBlank
    private String agentPhone;

    /**
     * 문의유형: 매매 | 전세 | 월세
     */
    @NotBlank
    private InquiryType type;

    /**
     * 문의자 이름 - 작성자만 볼 수 있음
     */
    private String customerName;

    /**
     * 문의자 연락처 - 작성자만 볼 수 있음
     */
    private String customerPhone;

    /**
     * 상세정보
     */
    private String apartmentName; //단지
    private double area; //면적
    private Long salePrice; //매매가
    private Long jeonsePrice; //전세가
    private Long deposit; //보증금
    private Long monthPrice; //월세


    private String memo;

    public InquiryPost toEntity()
    {

        return InquiryPost.builder()
                .title(title)
                .l1(l1)
                .l2(l2)
                .l3(l3)
                .agentName(agentName)
                .agentPhone(agentPhone)
                .type(type)
                .customerName(customerName)
                .customerPhone(customerPhone)

                .apartmentName(apartmentName)
                .area(area)
                .salePrice(salePrice)
                .jeonsePrice(jeonsePrice)
                .deposit(deposit)
                .monthPrice(monthPrice)
                .memo(memo)
                .status(InquiryStatus.IN_PROGRESS) //기본값
                .build();
    }
}
