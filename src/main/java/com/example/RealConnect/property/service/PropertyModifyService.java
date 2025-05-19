package com.example.RealConnect.property.service;

import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.dto.PropertyRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.domain.dto.PropertyStatusDto;
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
    public PropertyResponseDto modify(Long id, PropertyRequestDto dto, User agent){
        Property property = propertyRepository.findById(id)
                .orElse(null);

        propertyVerify(property);

        property.update(dto);

        return new PropertyResponseDto(property);
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
        return userRepository.findByUsername(name).get();
    }

    private void propertyVerify(Property property){
        if(property == null){
            throw new PropertyNotMatchException("매물등록이 되지 않았습니다.");
        }
    }
}
