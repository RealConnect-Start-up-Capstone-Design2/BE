package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.repository.ApartmentRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.PropertyStatus;
import com.example.RealConnect.property.domain.dto.PropertyCreateRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.exception.ApartmentNotFoundException;
import com.example.RealConnect.property.exception.ApartmentNotMatchException;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.user.domain.User;

// 매물 등록
@Service
@RequiredArgsConstructor
public class PropertyPostService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    public PropertyResponseDto save(PropertyCreateRequestDto dto, String name) {
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new ApartmentNotFoundException("존재하지 않는 아파트 ID"));

        User agent = userRepository.findByUsername(name).get();

        Property property = Property.builder()
                .apartment(apartment)
                .agent(agent)
                .ownerName(dto.getOwnerName())
                .ownerPhone(dto.getOwnerPhone())
                .tenantName(dto.getTenantName())
                .tenantPhone(dto.getTenantPhone())
                .salePrice(dto.getSalePrice())
                .jeonsePrice(dto.getJeonsePrice())
                .deposit(dto.getDeposit())
                .monthPrice(dto.getMonthPrice())
                .status(PropertyStatus.WAITING) // 기본값 처리
                .memo(dto.getMemo())
                .build();

        propertyRepository.save(property);
        return new PropertyResponseDto(property);
    }
}
