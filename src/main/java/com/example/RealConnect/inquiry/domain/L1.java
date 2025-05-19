package com.example.RealConnect.inquiry.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum L1 {
    SEOUL("서울특별시")
    ;

    private final String code;

    public static List<String> codes()
    {
        return Arrays.stream(values())
                .map(L1::getCode)
                .collect(Collectors.toList());
    }

}
