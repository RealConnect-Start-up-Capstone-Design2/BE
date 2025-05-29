package com.example.RealConnect.inquiry.domain;

import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InquiryPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User agent;

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
    @Enumerated(EnumType.STRING)
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
    private String area; //면적
    private Long salePrice; //매매가
    private Long jeonsePrice; //전세가
    private Long deposit; //보증금
    private Long monthPrice; //월세


    private String memo;

    /**
     * 진행상태: 진행중 | 완료
     */
    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    @CreatedDate
    private LocalDateTime createdAt;


    public InquiryPost withAgent(User agent)
    {
        this.agent = agent;
        return this;
    }
    public InquiryPost withDefaultPrices()
    {
        if(type.equals(InquiryType.BUY))
        {
            jeonsePrice = 0L;
            deposit = 0L;
            monthPrice = 0L;
        }
        else if(type.equals(InquiryType.JEONSE))
        {
            salePrice = 0L;
            deposit = 0L;
            monthPrice = 0L;
        }
        else
        {
            jeonsePrice = 0L;
            salePrice = 0L;
        }
        return this;
    }

    public InquiryPost modifyAll(InquiryPostCreateRequestDto requestDto)
    {
        title = requestDto.getTitle();
        l1 = requestDto.getL1();
        l2 = requestDto.getL2();
        l3 = requestDto.getL3();
        agentName = requestDto.getAgentName();
        agentPhone = requestDto.getAgentPhone();
        type = requestDto.getType();
        customerName = requestDto.getCustomerName();
        customerPhone = requestDto.getCustomerPhone();
        apartmentName = requestDto.getApartmentName();
        area = String.valueOf(requestDto.getArea());
        salePrice = requestDto.getSalePrice();
        jeonsePrice = requestDto.getJeonsePrice();
        deposit = requestDto.getDeposit();
        monthPrice = requestDto.getMonthPrice();
        memo = requestDto.getMemo();

        return this;
    }
}
