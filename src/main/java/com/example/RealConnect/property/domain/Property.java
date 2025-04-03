package com.example.RealConnect.property.domain;

import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.customer.domain.Customer;
import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /*
        아파트 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Apartment apartment;

    /*
        중개사
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User agent;

    /*
        소유자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "owner_id")
    private Customer owner;

    /*
        임차인
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "tenant_id")
    private Customer tenant;

    /*
        매매가
     */
    private Long salePrice;

    /*
        전세가
     */
    private Long jeonsePrice;

    /*
        보증금(월세인 경우)
     */
    private Long deposit;

    /*
        월세
     */
    private Long monthPrice;

    /*
        상태
     */
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    private String memo;


}
