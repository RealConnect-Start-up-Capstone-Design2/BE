package com.example.RealConnect.inquiry.service;

import com.example.RealConnect.inquiry.domain.Inquiry;
import com.example.RealConnect.inquiry.domain.InquiryPost;

import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequest;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostResponseDto;

import com.example.RealConnect.inquiry.exception.AccessDeniedException;
import com.example.RealConnect.inquiry.exception.InquiryPostNotFoundException;
import com.example.RealConnect.inquiry.repository.InquiryPostRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ShareService {

    private final UserRepository userRepository;
    private final InquiryPostRepository inquiryPostRepository;
    private final EntityManager em;

    /**
     * 공유글 생성
     * @param username
     * @param requestDto
     */
    public InquiryPostResponseDto share(String username, InquiryPostCreateRequestDto requestDto)
    {
        //로그인되어있으니 존재한다고 가정.
        User user = userRepository.findByUsername(username).get();


        InquiryPost post = requestDto.toEntity()
                        .withAgent(user)
                        .withDefaultPrices();

        return InquiryPostResponseDto.toDto(inquiryPostRepository.save(post));
    }

    /**
     * 공유글 1개 조회
     * @param username
     * @param id 공유글 id
     * @return
     */
    public InquiryPostResponseDto getShare(String username, Long id)
    {
        User user = userRepository.findByUsername(username).get();
        InquiryPost post = findInquiryPostOrThorw(id);

        return InquiryPostResponseDto.toDto(post, user);

    }
    /**
     * 내 공유글 조회
     * @param username
     * @return
     */
    public List<InquiryPostResponseDto> myInquiryPosts(String username)
    {
        User user = userRepository.findByUsername(username).get();
        List<InquiryPost> list = inquiryPostRepository.findInquiryPostsByAgent(user);

        List<InquiryPostResponseDto> dtoList = new ArrayList<>();
        for(InquiryPost post : list)
        {
            dtoList.add(InquiryPostResponseDto.toDto(post));
        }
        return dtoList;
    }

    /**
     * 공유글 삭제
     * @param username 사용자 ID
     * @param id 공유글 ID
     */
    public void delete(String username, Long id)
    {
        InquiryPost post = findInquiryPostOrThorw(id);
        User user = userRepository.findByUsername(username).get();

        //삭제 권한 검증
        checkOwnershipOrThrow(post, user);

        inquiryPostRepository.delete(post);
    }



    public InquiryPostResponseDto modify(String username, Long id, InquiryPostCreateRequestDto requestDto)
    {
        User user = userRepository.findByUsername(username).get();

        InquiryPost post = findInquiryPostOrThorw(id);
        checkOwnershipOrThrow(post, user);

        post.modifyAll(requestDto)
                .withDefaultPrices();
        return InquiryPostResponseDto.toDto(post);
    }

    /**
     * l1, l2, l3에 해당하는 공유글 조회
     * 타인의 글인 경우, 문의자 정보 숨김
     * @param username
     * @param l1
     * @param l2
     * @param l3
     * @return
     */
    public List<InquiryPostResponseDto> searchInquiryPosts(String username, String l1, String l2, String l3)
    {
        User user = userRepository.findByUsername(username).get();

        List<InquiryPost> list;
        /* 모든 문의글 반환 */
        if(l1.isBlank())
        {
            list = inquiryPostRepository.findAll();
        }
        /* 시 전체 반환 */
        else if(l2.isBlank())
        {
            list = inquiryPostRepository.findInquiryPostsByL1(l1);
        }
        /* 구 전체 반환 */
        else if(l3.isBlank())
        {
            list = inquiryPostRepository.findInquiryPostsByL1AndL2(l1, l2);
        }
        /* 동 전체 반환 */
        else
        {
            list = inquiryPostRepository.findInquiryPostsByL1AndL2AndL3(l1, l2, l3);
        }

        List<InquiryPostResponseDto> dtoList = new ArrayList<>();
        for(int i=0; i<list.size(); i++)
        {
            dtoList.add(InquiryPostResponseDto.toDto(list.get(i), user));
        }
        return dtoList;
    }

    private InquiryPost findInquiryPostOrThorw(Long id)
    {
        return inquiryPostRepository.findById(id)
                .orElseThrow(() -> new InquiryPostNotFoundException("잘못된 공유글 ID"));
    }

    private void checkOwnershipOrThrow(InquiryPost post, User user)
    {
        if(!post.getAgent().equals(user)) throw new AccessDeniedException("삭제 권한 없음");
    }

    // 문의 관리에서 '공유하기' 클릭하여 문의 공유
    public InquiryPostResponseDto shareFromInquiry(String username, Long inquiryId, InquiryPostCreateRequest request){
        // DB에서 해당 유저 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        // inquiryId에 해당하는 Inquiry 객체 조회
        Inquiry inquiry = em.find(Inquiry.class, inquiryId);
        if (inquiry == null || !inquiry.getAgent().equals(user)) {
            throw new AccessDeniedException("해당 문의에 접근할 권한이 없습니다.");
        }

        // Inquiry 정보를 inquiryPost 정보로 빌드
        InquiryPost post = InquiryPost.builder()
                .agent(user)
                .title(request.getTitle()) // 프론트에서 입력 값 받아서 사용
                .l1(request.getL1()) // 추후 위치 정보 파싱 가능
                .l2(request.getL2()) //
                .l3(request.getL3()) // 프론트에서 입력 받은 값으로 사용
                .agentName(user.getName()) // 업소명
                .agentPhone(request.getAgentPhone()) // 연락처는 프론트에서 입력 받은 값으로 사용
                .type(inquiry.getType())
                .customerName(inquiry.getName())
                .customerPhone(inquiry.getPhone())
                .apartmentName(inquiry.getApartmentName())
                .area(inquiry.getArea()) // string 형으로
                .salePrice(inquiry.getSalePrice())
                .jeonsePrice(inquiry.getJeonsePrice())
                .deposit(inquiry.getDeposit())
                .monthPrice(inquiry.getMonthPrice())
                .memo(request.getMemo()) // 프론트에서 입력 받은 값으로 사용
                .status(inquiry.getStatus())
                .build()
                .withDefaultPrices();

        inquiryPostRepository.save(post);
        return InquiryPostResponseDto.toDto(post);
    }


}
