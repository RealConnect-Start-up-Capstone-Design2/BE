package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.repository.ApartmentRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.PropertyStatus;
import com.example.RealConnect.property.domain.dto.ApartmentPropertyDto;
import com.example.RealConnect.property.domain.dto.PropertyCreateRequestDto;
import com.example.RealConnect.property.exception.ApartmentNotFoundException;
import com.example.RealConnect.property.exception.PropertyAlreadyExistsException;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.user.domain.User;
import org.springframework.util.StringUtils;

// 매물 등록
@Service
@RequiredArgsConstructor
public class PropertyPostService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    /**
     * Property를 생성한다.
     * 예외처리
     * - 존재하지 않는 아파트 ID
     * - 아파트-매물 1:1 로직을 위반한 생성
     *
     * @param dto
     * @param name 사용자 ID
     * @return
     */
    public ApartmentPropertyDto save(PropertyCreateRequestDto dto, String name) {

        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new ApartmentNotFoundException("아파트ID가 존재하지 않습니다."));

        User agent = userRepository.findByUsername(name).get();

        verifyDuplicate(agent, apartment);

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
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .memo(dto.getMemo())
                .build();

        property = propertyRepository.save(property);
        return ApartmentPropertyDto.toDto(property, apartment);
    }

    private void verifyDuplicate(User user, Apartment apartment)
    {
        if(propertyRepository.existsByApartmentAndAgent(apartment, user))
        {
            throw new PropertyAlreadyExistsException("아파트ID에 해당하는 매물이 존재해 새로 생성할 수 없습니다.");
        }
    }

    /**
     * 매물의 소유자, 임차인으로 Status를 결정해주는 메서드
     * 현재 사용X
     * @param property
     */
    private void checkStatus(Property property)
    {
        boolean isOwner = StringUtils.hasText(property.getOwnerName());
        boolean isTenant = StringUtils.hasText(property.getTenantName());

        if(isOwner && isTenant) //임대인, 임차인 정보 모두 있는 경우
        {
            property.changeStatus(PropertyStatus.RESERVED);
        }
        else
        {
            property.changeStatus(PropertyStatus.WAITING);
        }
    }
}
