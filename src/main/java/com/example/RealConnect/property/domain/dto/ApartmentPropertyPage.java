package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.apartment.domain.Direction;
import com.example.RealConnect.property.domain.PropertyStatus;

import java.time.LocalDate;

public interface ApartmentPropertyPage {

    // ─ Apartment ─
    Long getApartmentId();
    String getApartmentName();
    String getDong();
    String getHo();
    String getArea();
    String getType();
    Direction getDirection();
    String getImg();

    // ─ Property (nullable) ─
    PropertySlice getProperty();     // null ⇒ 매물 없음

    interface PropertySlice {
        Long getId();
        PropertyStatus getStatus();

        String getOwnerName();
        String getOwnerPhone();

        String getTenantName();
        String getTenantPhone();

        Long getSalePrice();
        Long getJeonsePrice();
        Long getDeposit();
        Long getMonthPrice();
        LocalDate getStartDate();
        LocalDate getEndDate();
        String getMemo();
    }
}
