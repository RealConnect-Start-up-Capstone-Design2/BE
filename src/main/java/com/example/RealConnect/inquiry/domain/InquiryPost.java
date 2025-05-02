package com.example.RealConnect.inquiry.domain;

import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String phone;

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
    private double area; //면적
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

}
