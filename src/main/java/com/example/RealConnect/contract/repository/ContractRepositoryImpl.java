package com.example.RealConnect.contract.repository;

import com.example.RealConnect.contract.Exception.InvalidContractTypeException;
import com.example.RealConnect.contract.domain.Contract;
import com.example.RealConnect.contract.domain.ContractType;
import com.example.RealConnect.user.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// QueryDSL에서 Q타입을 사용하기 위한 import문 - 엔티티 클래스의 픽드를 쿼리 조건에 사용할 수 있게 된다.
import static com.example.RealConnect.contract.domain.QContract.contract; //

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepositoryCustom{

    // QueryDSL 기반 검색 로직 - 쿼리를 자바코드로

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Contract> searchContracts(Boolean favorite, String type, String keyword, User user){

        // Boolean builder - queryDSL 사용 시 조건을 동적으로 구성할 때 사용하는 유틸리티 클래스
        // and, or 조건 동적으로 추가 가능
        BooleanBuilder builder = new BooleanBuilder();


        builder.and(contract.agent.id.eq(user.getId()));  ///
        System.out.println(user.getUsername());

        // 즐겨찾기 여부 필터링
        if(favorite != null){
            builder.and(contract.favorite.eq(favorite));
        }

        // 계약 유형 필터링 (전체 / 매매 / 전세 / 월세) - 거래 유형이 '전체'가 아닌 경우 필터링 적용
        if(type != null && !type.isEmpty() && !type.equalsIgnoreCase("ALL")){
            try {
                builder.and(contract.type.eq(ContractType.valueOf(type.toUpperCase())));
            } catch (IllegalArgumentException e) {
                throw new InvalidContractTypeException("유효하지 않은 계약 유형입니다.");
            }
        }

        // 키워드로 검색(단지명, 임차인 이름)
        if (keyword != null && !keyword.isBlank()) {
            builder.and(
                    contract.apartment.containsIgnoreCase(keyword)
                            .or(contract.tenantName.containsIgnoreCase(keyword))
            );
        }

        return jpaQueryFactory
                .selectFrom(contract)
                .where(builder)
                .orderBy(contract.id.desc()) // 최신 계약부터 조회
                .fetch();
    }
}
