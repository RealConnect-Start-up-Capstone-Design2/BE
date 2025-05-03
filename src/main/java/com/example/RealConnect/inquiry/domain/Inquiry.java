package com.example.RealConnect.inquiry.domain;

import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import com.example.RealConnect.inquiry.domain.InquiryStatus;
import com.example.RealConnect.inquiry.domain.InquiryType;
import java.time.LocalDateTime;

/**
 * 문의고객정보
 */

@Entity
@Table(name = "inquiry") //
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

    // 등록일
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 진행 상태 - enum(진행 중, 진행 완료)
    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    /**
     * 문의고객 이름
     */
    private String name;

    /**
     * 문의고객 전화번호
     */
    private String phone;

 ///////////////////////요구사항//////////////////////////////////////////

    /**
     * 문의유형
     *  매매| 전세 | 월세 | 전체
     */
    // 진행 상태 - enum(전체, 진행 중, 진행 완료)
    @Enumerated(EnumType.STRING)
    private InquiryType type;


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

    // 필드 update 메소드
    public void update(String name, String phone, String apartmentName, InquiryType type,
                       InquiryStatus status, Long salePrice, Long jeonsePrice, Long monthPrice,
                       String memo, boolean favorite) {
        this.name = name;
        this.phone = phone;
        this.apartmentName = apartmentName;
        this.type = type;
        this.status = status;
        this.salePrice = salePrice;
        this.jeonsePrice = jeonsePrice;
        this.monthPrice = monthPrice;
        this.memo = memo;
        this.favorite = favorite;
    }
}
