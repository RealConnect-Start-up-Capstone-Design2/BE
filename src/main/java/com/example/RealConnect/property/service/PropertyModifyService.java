package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.apartment.repository.ApartmentRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyStatusDto;
import com.example.RealConnect.property.exception.ApartmentNotMatchException;
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
    private final ApartmentRepository apartmentRepository;

    // 매물 정보 수정
    @Transactional
    public boolean modify(Long id, PropertyRequestDto dto, User agent){
        Property property = propertyRepository.findById(id)
                .orElse(null);
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElse(null);

        propertyVerify(property);
        apartmentVerify(apartment);

        property.update(dto, apartment, agent);
        return true;
    }

    // 매물 상태 변경
    @Transactional
    public boolean changeStatus(Long id, PropertyStatusDto dto){
        Property property = propertyRepository.findById(id)
                .orElse(null);

        propertyVerify(property);

        property.changeStatus(dto.getStatus());
        return true;
    }

    public User findUser(String name){
        return userRepository.findById(Long.valueOf(name)).get();
    }

    private void propertyVerify(Property property){
        if(property == null){
            throw new PropertyNotMatchException("매물등록이 되지 않았습니다.");
        }
    }

    private void apartmentVerify(Apartment apartment) {
        if(apartment == null)
        {
            throw new ApartmentNotMatchException("아파트 등록이 되지 않았습니다.");
        }
    }
}
