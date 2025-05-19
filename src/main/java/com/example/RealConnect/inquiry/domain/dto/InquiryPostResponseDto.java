package com.example.RealConnect.inquiry.domain.dto;

import com.example.RealConnect.inquiry.domain.InquiryPost;
import com.example.RealConnect.inquiry.domain.InquiryType;
import com.example.RealConnect.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquiryPostResponseDto {

    private Long id;

    /**
     * 문의 공유 제목
     */
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
    private String agentName;

    /**
     * 업소 연락처
     */
    private String agentPhone;

    /**
     * 문의유형: 매매 | 전세 | 월세
     */
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


    public void hide()
    {
        customerName = null;
        customerPhone = null;
    }

    public static InquiryPostResponseDto toDto(InquiryPost p)
    {
        return InquiryPostResponseDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .l1(p.getL1())
                .l2(p.getL2())
                .l3(p.getL3())
                .agentName(p.getAgentName())
                .agentPhone(p.getAgentPhone())
                .type(p.getType())
                .customerName(p.getCustomerName())
                .customerPhone(p.getCustomerPhone())

                .apartmentName(p.getApartmentName())
                .area(p.getArea())
                .salePrice(p.getSalePrice())
                .jeonsePrice(p.getJeonsePrice())
                .deposit(p.getDeposit())
                .monthPrice(p.getMonthPrice())
                .memo(p.getMemo())
                .build();

    }

    /**
     * 고객정보 숨김이 필요한 경우
     * @param p
     * @param user 요청자
     * @return
     */
    public static InquiryPostResponseDto toDto(InquiryPost p, User user)
    {
        InquiryPostResponseDto dto = InquiryPostResponseDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .l1(p.getL1())
                .l2(p.getL2())
                .l3(p.getL3())
                .agentName(p.getAgentName())
                .agentPhone(p.getAgentPhone())
                .type(p.getType())
                .customerName(p.getCustomerName())
                .customerPhone(p.getCustomerPhone())

                .apartmentName(p.getApartmentName())
                .area(p.getArea())
                .salePrice(p.getSalePrice())
                .jeonsePrice(p.getJeonsePrice())
                .deposit(p.getDeposit())
                .monthPrice(p.getMonthPrice())
                .memo(p.getMemo())
                .build();

        if(!p.getAgent().equals(user))
        {
            dto.hide();
        }
        return dto;
    }
}
