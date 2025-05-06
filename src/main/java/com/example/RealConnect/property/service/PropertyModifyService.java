package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.apartment.repository.ApartmentRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyStatusDto;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public boolean modify(Long id, PropertyRequestDto dto){
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("매물 없음"));

        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new EntityNotFoundException("아파트 없음"));

        User agent = userRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("중개사 없음"));

        property.update(dto, apartment, agent);
        return true;
    }

    // 매물 상태 변경
    @Transactional
    public boolean changeStatus(Long id, PropertyStatusDto dto){
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 매물이 존재하지 않습니다."));
        property.changeStatus(dto.getStatus());
        return true;
    }
}
