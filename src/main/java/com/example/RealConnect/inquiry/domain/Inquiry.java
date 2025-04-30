package com.example.RealConnect.inquiry.domain;

import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 문의고객정보
 */

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 문의 담당 중개사
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User agent;

    /**
     * 문의고객 이름
     */
    private String name;

    /**
     * 문의고객 전화번호
     */
    private String phone;

 ///////////////////////요구사항//////////////////////////////////

    /**
     * 문의유형
     *  매매| 전세 | 월세
     */
    private String inquiryType;
    private String apartmentName; //단지
    private double area; //면적
    private Long salePrice; //매매가
    private Long jeonsePrice; //전세가
    private Long deposit; //보증금
    private Long monthPrice; //월세
///////////////////////////////////////////////////////////////////////

    /**
     * 문의내용
     */
    private String memo;

    /**
        즐겨찾기
     */
    private boolean favorite;
}
