package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.repository.ApartmentRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.PropertyStatus;
import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.exception.ApartmentNotMatchException;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.repository.UserRepository;
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

    public boolean save(PropertyRequestDto dto, String name) {
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElse(null);
        User agent = findUser(name);

        apartmentVerify(apartment);

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

    public User findUser(String name){
        return userRepository.findById(Long.valueOf(name)).get();
    }

    private void apartmentVerify(Apartment apartment) {
        if(apartment == null)
        {
            throw new ApartmentNotMatchException("아파트 등록이 되지 않았습니다.");
        }
    }
}
