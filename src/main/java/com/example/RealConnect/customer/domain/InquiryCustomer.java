package com.example.RealConnect.customer.domain;

import com.example.RealConnect.apartment.domain.Direction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class InquiryCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

 ///////////////////////요구사항//////////////////////////////////
    private String apartmentName;
    private int Dong;
    private int Ho;
    private double area;
    private Direction direction;
    private Long salePrice; //매매가
    private Long jeonsePrice; //전세가
    private Long deposit; //보증금
    private Long monthPrice; //월세
///////////////////////////////////////////////////////////////////////


    private String memo;

    /*
        즐겨찾기
     */
    private boolean favorite;
}
