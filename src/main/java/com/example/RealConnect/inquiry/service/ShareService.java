package com.example.RealConnect.inquiry.service;

import com.example.RealConnect.inquiry.domain.InquiryPost;
import com.example.RealConnect.inquiry.domain.InquiryType;
import com.example.RealConnect.inquiry.domain.dto.InquiryPostCreateRequestDto;
import com.example.RealConnect.inquiry.repository.InquiryPostRepository;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class ShareService {

    private final UserRepository userRepository;
    private final InquiryPostRepository inquiryPostRepository;

    public void share(String username, InquiryPostCreateRequestDto requestDto)
    {
        //로그인되어있으니 존재한다고 가정.
        User user = userRepository.findByUsername(username).get();


        InquiryPost post = requestDto.toEntity()
                        .withAgent(user)
                        .withDefaultPrices();
        inquiryPostRepository.save(post);
    }
}
