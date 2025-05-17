package com.example.RealConnect.inquiry.repository;

import com.example.RealConnect.inquiry.domain.Inquiry;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.example.RealConnect.inquiry.domain.QInquiry.inquiry;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryRepositoryImpl implements InquiryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Inquiry> searchInquiriesByCondition(Long agentId, String status, String inquiryType,
                                                    String keyword, Boolean favoriteOnly) {
        return queryFactory
                .selectFrom(inquiry)
                .where(
                        inquiry.agent.id.eq(agentId),
                        status != null ? inquiry.status.stringValue().eq(status) : null,
                        inquiryType != null ? inquiry.type.stringValue().eq(inquiryType) : null,
                        keyword != null ? inquiry.name.containsIgnoreCase(keyword)
                                .or(inquiry.apartmentName.containsIgnoreCase(keyword)) : null,
                        favoriteOnly != null && favoriteOnly ? inquiry.favorite.isTrue() : null
                )
                .fetch();
    }
}
