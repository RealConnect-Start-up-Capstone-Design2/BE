package com.example.RealConnect.inquiry.service;

import com.example.RealConnect.inquiry.domain.InquiryPost;
import com.example.RealConnect.inquiry.domain.InquiryType;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostResponseDto;
import com.example.RealConnect.inquiry.exception.AccessDeniedException;
import com.example.RealConnect.inquiry.exception.InquiryPostNotFoundException;
import com.example.RealConnect.inquiry.repository.InquiryPostRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
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

    private InquiryPost findInquiryPostOrThorw(Long id)
    {
        return inquiryPostRepository.findById(id)
                .orElseThrow(() -> new InquiryPostNotFoundException("잘못된 공유글 ID"));
    }

    private void checkOwnershipOrThrow(InquiryPost post, User user)
    {
        if(!post.getAgent().equals(user)) throw new AccessDeniedException("삭제 권한 없음");
    }
}
