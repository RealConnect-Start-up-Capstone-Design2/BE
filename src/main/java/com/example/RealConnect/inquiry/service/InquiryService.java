package com.example.RealConnect.inquiry.service;

import com.example.RealConnect.inquiry.domain.Inquiry;
import com.example.RealConnect.inquiry.domain.dto.InquiryCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryResponseDto;
import com.example.RealConnect.inquiry.repository.InquiryRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.RealConnect.inquiry.domain.InquiryType;
import com.example.RealConnect.inquiry.domain.InquiryStatus;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    /*
    문의 등록 로직을 담당하는 service
    1. 로그안한 사용자(중개사)를 DB에서 조회
    2. Inquiry 객체 생성
    3. DB에 저장
    4. 응답용 DTO로 변환해서 반환
     */

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    // 문의 등록
    @Transactional
    public InquiryResponseDto register(InquiryCreateRequestDto dto, Long agentID) {
        // 1. 중개사 조회
        User agent = userRepository.findById(agentID)
                .orElseThrow(() -> new IllegalArgumentException("해당 중개사를 찾을 수 없습니다."));

        // 2. inquiry entity 생성
        Inquiry inquiry = Inquiry.builder()
                .agent(agent)
                .name(dto.getName())
                .phone(dto.getPhone())
                .type(InquiryType.valueOf(dto.getInquiryType())) //
                .apartmentName(dto.getApartmentName())
                .area(dto.getArea())
                .salePrice(dto.getSalePrice())
                .jeonsePrice(dto.getJeonsePrice())
                .deposit(dto.getDeposit())
                .monthPrice(dto.getMonthPrice())
                .memo(dto.getMemo())
                .status(InquiryStatus.IN_PROGRESS) // 초기 상태
                .favorite(false) // 초기 즐겨찾기 설정
                .build();

        // 3. DB에 저장
        Inquiry saved = inquiryRepository.save(inquiry);

        // 4. 응답용 DTO로 변환해서 반환
        return InquiryResponseDto.from(saved);
    }


    /*
    문의 검색을 위한 service
    1. 통합 검색 - 검색어로 검색(단지명, 문의자 이름)
    2. 문의 유형 드롭박스로 검색(전체, 매매, 전세, 월세)
    3. 진행 상채 드롭박스로 검색(전체, 진행 중, 진행 완료)
     */
    @Transactional(readOnly = true)
    public List<InquiryResponseDto> searchInquiries(Long agentID, String status, String inquiryType, String keyword) {
        List<Inquiry> inquiries = inquiryRepository.searchInquiriesByCondition(agentID, status, inquiryType, keyword);

        return inquiries.stream()
                .map(InquiryResponseDto::from)
                .collect(Collectors.toList());
    }
}
