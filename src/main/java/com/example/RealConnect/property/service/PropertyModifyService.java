package com.example.RealConnect.property.service;

import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.dto.ApartmentPropertyDto;
import com.example.RealConnect.property.domain.dto.PropertyModifyRequestDto;
import com.example.RealConnect.property.exception.PropertyNotFoundException;
import com.example.RealConnect.property.exception.PropertyNotMatchException;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyModifyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    // 매물 정보 수정
    @Transactional
    public ApartmentPropertyDto modify(Long propertyId, PropertyModifyRequestDto dto, String username){

        User user = userRepository.findByUsername(username).get();

        Property property = findPropertyOrThrow(propertyId);

        //권한 검증
        verifyPropertyOwner(property, user);

        property.update(dto);

        return ApartmentPropertyDto.toDto(property, property.getApartment());
    }

    /**
     * 매물이 존재하는 지 검증
     * @param propertyId
     * @return
     */
    private Property findPropertyOrThrow(Long propertyId)
    {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("존재하지 않는 매물 ID"));
    }

    /**
     * 매물 수정 권한 확인
     * @param property
     * @param user
     */
    private void verifyPropertyOwner(Property property, User user)
    {
        if(!property.getAgent().equals(user))
        {
            throw new PropertyNotMatchException("매물 수정 권한이 없습니다.");
        }
    }
}
