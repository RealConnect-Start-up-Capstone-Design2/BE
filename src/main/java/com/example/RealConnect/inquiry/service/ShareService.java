package com.example.RealConnect.inquiry.service;

import com.example.RealConnect.inquiry.domain.InquiryPost;
import com.example.RealConnect.inquiry.domain.InquiryType;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostResponseDto;
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
    public void share(String username, InquiryPostCreateRequestDto requestDto)
    {
        //로그인되어있으니 존재한다고 가정.
        User user = userRepository.findByUsername(username).get();


        InquiryPost post = requestDto.toEntity()
                        .withAgent(user)
                        .withDefaultPrices();
        inquiryPostRepository.save(post);
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
}
