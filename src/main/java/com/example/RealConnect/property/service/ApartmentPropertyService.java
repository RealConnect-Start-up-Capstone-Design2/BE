package com.example.RealConnect.property.service;

import com.example.RealConnect.apartment.repository.ApartmentRepository;
import com.example.RealConnect.property.domain.dto.ApartmentPropertyPage;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApartmentPropertyService {

    private final PropertyRepository propertyRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;

    /**
     * 매물 관리 [전체]
     * 동 호수 기준으로 모든 아파트 반환
     * 내 매물이 있다면 아파트에 매물 정보를 붙여 반환
     * 없다면 매물은 null
     *
     * page = 1, size = 1000 일 경우, 1001~2000까지 반환됨
     * page = 5, size = 500 일 경우, 2501~3000
     * @param username
     * @param page 요청한 페이지
     * @param size 페이지 당 개수
     * @return
     */
    public Page<ApartmentPropertyPage> findAllApartmentWithProperty(String username, int page, int size)
    {
        User user = userRepository.findByUsername(username).get();

        if(size <= 0) size = 1000;

        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        return apartmentRepository.findPageWithMyProperty(user, pageable);

    }
}
