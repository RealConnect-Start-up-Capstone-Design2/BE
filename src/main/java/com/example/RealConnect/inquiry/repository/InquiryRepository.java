package com.example.RealConnect.inquiry.repository;

import com.example.RealConnect.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {


    // Query DSL로
}
