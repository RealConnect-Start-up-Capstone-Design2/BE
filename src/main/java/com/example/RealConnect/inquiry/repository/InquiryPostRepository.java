package com.example.RealConnect.inquiry.repository;

import com.example.RealConnect.inquiry.domain.InquiryPost;
import com.example.RealConnect.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryPostRepository extends JpaRepository<InquiryPost, Long> {

    List<InquiryPost> findInquiryPostsByAgent(User agent);

    List<InquiryPost> findAll();

    List<InquiryPost> findInquiryPostsByL1(String l1);

    List<InquiryPost> findInquiryPostsByL1AndL2(String l1, String l2);

    List<InquiryPost> findInquiryPostsByL1AndL2AndL3(String l1, String l2, String l3);


}
