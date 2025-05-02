package com.example.RealConnect.inquiry.domain;

import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
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
     * 문의 지역 구분: 시 | 구 | 동
     */
    private String l1;
    private String l2;
    private String l3;

    /**
     * 업소명
     */
    private String agentName;

    /**
     * 연락처
     */
    private String phone;

    @Enumerated(EnumType.STRING)
    private InquiryType type;


    private String apartmentName; //단지
    private double area; //면적
    private Long salePrice; //매매가
    private Long jeonsePrice; //전세가
    private Long deposit; //보증금
    private Long monthPrice; //월세


    @CreatedDate
    private LocalDateTime createdAt;

}
