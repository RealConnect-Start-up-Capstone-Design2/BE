package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.apartment.domain.Direction;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.PropertyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

// 생성된 property 반환 dto
@Getter
@Builder
@AllArgsConstructor
public class ApartmentPropertyDto {

    // ─ Apartment ─
    Long ApartmentId;
    String ApartmentName;
    String dong;
    String ho;
    String area;
    String type;
    Direction direction;
    String img;

    // ─ Property ─
    Long propertyId;
    PropertyStatus status;

    String ownerName;
    String ownerPhone;

    String tenantName;
    String tenantPhone;

    Long salePrice;
    Long jeonsePrice;
    Long deposit;
    Long monthPrice;
    LocalDate startDate;
    LocalDate endDate;
    String memo;

    public static ApartmentPropertyDto toDto(Property property, Apartment apartment)
    {
        return ApartmentPropertyDto.builder()
                .ApartmentId(apartment.getId())
                .ApartmentName(apartment.getName())
                .dong(apartment.getDong())
                .ho(apartment.getHo())
                .area(apartment.getArea())
                .type(apartment.getType())
                .direction(apartment.getDirection())
                .img(apartment.getImg())

                .propertyId(property.getId())
                .status(property.getStatus())
                .ownerName(property.getOwnerName())
                .ownerPhone(property.getOwnerPhone())
                .tenantName(property.getTenantName())
                .tenantPhone(property.getTenantPhone())
                .salePrice(property.getSalePrice())
                .jeonsePrice(property.getJeonsePrice())
                .deposit(property.getDeposit())
                .monthPrice(property.getMonthPrice())
                .startDate(property.getStartDate())
                .endDate(property.getEndDate())
                .memo(property.getMemo())
                .build();
    }

}