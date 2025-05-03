package com.example.RealConnect.apartment.domain;

import lombok.Getter;

@Getter
public enum Direction {
    NORTH("북향"),
    SOUTH("남향"),
    EAST("동향"),
    WEST("서향"),
    NORTHEAST("북동향"),
    SOUTHEAST("남동향"),
    SOUTHWEST("남서향"),
    NORTHWEST("북서향");

    private String direction;

    Direction(String direction) {
        this.direction = direction;
    }

}
