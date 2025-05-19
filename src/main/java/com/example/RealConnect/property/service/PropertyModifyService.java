package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.repository.ApartmentRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.PropertyStatus;
import com.example.RealConnect.property.domain.dto.PropertyCreateRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyModifyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.domain.dto.PropertyStatusDto;
import com.example.RealConnect.property.exception.ApartmentNotFoundException;
import com.example.RealConnect.property.exception.IllegalPropertyStatusException;
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
    public PropertyResponseDto modify(Long propertyId, PropertyModifyRequestDto dto, String username){

        User user = userRepository.findByUsername(username).get();

        Property property = findPropertyOrThrow(propertyId);

        //권한 검증
        verifyPropertyOwner(property, user);

        property.update(dto);

        return new PropertyResponseDto(property);
    }

    // 매물 상태 변경
    @Transactional
    public boolean changeStatus(Long propertyId, PropertyStatusDto dto){
        Property property = findPropertyOrThrow(propertyId);

        PropertyStatus st;
        if(dto.getStatus()==0)
        {
            st = PropertyStatus.WAITING;
        }
        else if(dto.getStatus()==1)
        {
            st = PropertyStatus.RESERVED;
        }
        else if(dto.getStatus()==2)
        {
            st = PropertyStatus.CONTRACTED;
        }
        else throw new IllegalPropertyStatusException("잘못된 상태 변경");

        property.changeStatus(st);
        return true;
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
