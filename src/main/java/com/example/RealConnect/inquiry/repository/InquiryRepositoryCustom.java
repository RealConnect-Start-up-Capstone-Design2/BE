package com.example.RealConnect.inquiry.repository;

import com.example.RealConnect.inquiry.domain.Inquiry;

import java.util.List;

public interface InquiryRepositoryCustom {
    List<Inquiry> searchInquiriesByCondition(Long agentID, String status, String inquiryType, String keyword);
}
