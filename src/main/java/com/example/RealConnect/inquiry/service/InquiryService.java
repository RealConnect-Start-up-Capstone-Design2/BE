package com.example.RealConnect.inquiry.service;

import com.example.RealConnect.inquiry.domain.Inquiry;
import com.example.RealConnect.inquiry.domain.dto.InquiryCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryResponseDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryUpdateRequest;
import com.example.RealConnect.inquiry.exception.AccessDeniedException;
import com.example.RealConnect.inquiry.exception.InquiryNotFoundException;
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
    public InquiryResponseDto register(InquiryCreateRequestDto dto, String username) {
        // 1. 중개사 조회
        User agent = userRepository.findByUsername(username).get();

        // 2. inquiry entity 생성
        Inquiry inquiry = Inquiry.builder()
                .agent(agent)
                .name(dto.getName())
                .phone(dto.getPhone())
                .type(InquiryType.valueOf(dto.getInquiryType())) //
                .apartmentName(dto.getApartmentName())
                .area(String.valueOf(dto.getArea())) //
                .salePrice(dto.getSalePrice())  //////
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
    @Transactional(readOnly = true) ////////////////
    public List<InquiryResponseDto> searchInquiries(String username, String status, String inquiryType,
                                                    String keyword, Boolean favorite) {//

        User agent = userRepository.findByUsername(username).get();

        List<Inquiry> inquiries = inquiryRepository.searchInquiriesByCondition(agent.getId(), status, inquiryType,
                keyword, favorite);//

        return inquiries.stream()
                .map(InquiryResponseDto::from)
                .collect(Collectors.toList());
    }

    /*
    문의 수정을 위한 service
    -중개사 본인 확인 로직: 보안을 위한 것, 서버에서 jwt 1차 인증 / DB에서 2차 확인
    1. 단지 이름 수정 가능 - aprtmentName
    2. 문의 유형 드롭박스로 수정 가능(매매, 전세, 월세) - BUY, JEONSE, MONTH_RENT
    3. 진행 상태 드롭박스로 수정 가능(진행 중, 진행 완료) - IN_PROGRESS, COMPLETED
    4. 희망 가격 수정 가능 - 매매라면 salePrice, 전세라면 jeonsePrice, 월세라면 monthPrice
    5. 문의 내용 수정 가능 - memo
    6. 문의자 수정 가능 - name
    7. 문의고객 연락처 수정 가능 - phone
    8. 즐겨찾기에 추가 가능 - favorite
     */

    @Transactional
    public InquiryResponseDto updateInquiry(Long inquiryID, InquiryUpdateRequest dto, String username) {

        User agent = userRepository.findByUsername(username).get();

        // 해당 문의 조회
        Inquiry inquiry = inquiryRepository.findById(inquiryID)
                .orElseThrow(() -> new InquiryNotFoundException("존재하지 않는 문의 ID")); // 예외처리

        // 중개사 본인 확인 - DB에서 2차 확인
        if (!inquiry.getAgent().getId().equals(agent.getId())) {
            throw new AccessDeniedException("문의 수정 권한이 없습니다.");
        }

        // 필드 값 수정
        inquiry.update(
                dto.getName(),
                dto.getPhone(),
                dto.getApartmentName(),
                dto.getType(),
                dto.getStatus(),
                dto.getSalePrice(),
                dto.getJeonsePrice(),
                dto.getMonthPrice(),
                dto.getMemo(),
                dto.isFavorite()
        );

        // 수정 결과 반환
        return InquiryResponseDto.from(inquiry);
    }

    /*
    문의 삭제를 위한 Service
     */
    @Transactional
    public void deleteInquiry(Long inquiryId, String username) {

        User agent = userRepository.findByUsername(username).get();

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new InquiryNotFoundException("해당 문의가 존재하지 않습니다."));

        if (!inquiry.getAgent().getId().equals(agent.getId())) {
            throw new AccessDeniedException("문의 삭제 권한이 없습니다.");
        }

        inquiryRepository.delete(inquiry);
    }
}
