package com.example.RealConnect.apartment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * 아파트 정보
 *
 */
@Entity
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
    private int Dong;

    /*
        호
     */
    private int Ho;

    /*
        면적
     */
    private double area;

    /*
        방향(8)
     */
    private Direction direction;

    /*
        이미지 url? path?
     */
    private String img;
}
