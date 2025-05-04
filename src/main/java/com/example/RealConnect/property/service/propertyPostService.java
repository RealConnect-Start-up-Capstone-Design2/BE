package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.repository.apartmentRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.PropertyStatus;
import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.repository.propertyRepository;
import com.example.RealConnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.user.domain.User;

// 매물 등록
@Service
@RequiredArgsConstructor
public class propertyPostService {

    private final propertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final apartmentRepository apartmentRepository;

    public boolean save(PropertyRequestDto dto) {
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new IllegalArgumentException("아파트가 존재하지 않습니다."));
        User agent = userRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new IllegalArgumentException("중개사가 존재하지 않습니다."));

        Property property = Property.builder()
                .apartment(apartment)
                .agent(agent)
                .ownerName(dto.getOwnerName())
                .ownerPhone(dto.getOwnerPhone())
                .tenantName(dto.getTenantName())
                .tenantPhone(dto.getTenantPhone())
                .isSale(dto.isSale())
                .salePrice(dto.getSalePrice())
                .isJeonse(dto.isJeonse())
                .jeonsePrice(dto.getJeonsePrice())
                .isMonth(dto.isMonth())
                .deposit(dto.getDeposit())
                .monthPrice(dto.getMonthPrice())
                .status(dto.getStatus() != null ? dto.getStatus() : PropertyStatus.WAITING) // 기본값 처리
                .memo(dto.getMemo())
                .build();

        propertyRepository.save(property);
        return true;
    }
}
