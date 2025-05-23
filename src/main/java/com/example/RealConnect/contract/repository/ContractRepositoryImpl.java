package com.example.RealConnect.contract.repository;

import com.example.RealConnect.contract.domain.Contract;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepositoryCustom{

    // QueryDSL 기반 검색 로직 - 쿼리를 자바코드로

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Contract> searchContracts(Boolean favorite, String type, String keyword){

    }
}
