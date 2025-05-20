package com.example.RealConnect.property.service;

import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.dto.PropertyResponseDto;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyFindService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    // 모든 매물 찾기
    public List<PropertyResponseDto> findAll() {
        List<Property> properties = propertyRepository.findAll();

        List<PropertyResponseDto> propertyResponseDtos = new ArrayList<>();
        for (Property property : properties) {
            propertyResponseDtos.add(new PropertyResponseDto(property));
        }

        return propertyResponseDtos;
    }

    // 특정 매물 찾기

    // 내 매물 찾기
    public List<PropertyResponseDto> findMine(String name) {
        User agents = userRepository.findByUsername(name).get();
        List<Property> properties = propertyRepository.findAllByAgent(agents);
        List<PropertyResponseDto> propertyResponseDtos = new ArrayList<>();

        for(Property property : properties) {
            propertyResponseDtos.add(new PropertyResponseDto(property));
        }

        return propertyResponseDtos;
    }
}
