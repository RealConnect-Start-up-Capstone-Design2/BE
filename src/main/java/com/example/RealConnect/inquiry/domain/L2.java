package com.example.RealConnect.inquiry.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum L2 {

    JONGNO("종로구", L1.SEOUL),
    JUNG("중구", L1.SEOUL),
    YONGSAN("용산구", L1.SEOUL),
    SEONGDONG("성동구", L1.SEOUL),
    GWANGJIN("광진구", L1.SEOUL),
    DONGDAEMUN("동대문구", L1.SEOUL),
    JUNGNANG("중랑구", L1.SEOUL),
    SEONGBUK("성북구", L1.SEOUL),
    GANGBUK("강북구", L1.SEOUL),
    DOBONG("도봉구", L1.SEOUL),
    NOWON("노원구", L1.SEOUL),
    EUNPYEONG("은평구", L1.SEOUL),
    SEODAEMUN("서대문구", L1.SEOUL),
    MAPO("마포구", L1.SEOUL),
    YANGCHEON("양천구", L1.SEOUL),
    GANGSEO("강서구", L1.SEOUL),
    GURO("구로구", L1.SEOUL),
    GEUMCHEON("금천구", L1.SEOUL),
    YEONGDEUNGPO("영등포구", L1.SEOUL),
    DONGJAK("동작구", L1.SEOUL),
    GWANAK("관악구", L1.SEOUL),
    SEOCHO("서초구", L1.SEOUL),
    GANGNAM("강남구", L1.SEOUL),
    SONGPA("송파구", L1.SEOUL),
    GANGDONG("강동구", L1.SEOUL);

    private final String code;
    private final L1 l1;
}
