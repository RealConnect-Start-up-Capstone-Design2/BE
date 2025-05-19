package com.example.RealConnect.apartment.domain;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 아파트 정보
 *
 */
@Entity
@Getter
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        단지명(아파트명)
     */
    private String name;

    /*
        동
     */
    private String Dong;

    /*
        호
     */
    private String Ho;

    /*
        면적
     */
    private String area;

    /*
        타입
     */
    private String type;

    /*
        방향(8)
     */
    @Enumerated(value = EnumType.STRING)
    private Direction direction;

    /*
        이미지 url? path?
     */
    private String img;
}
