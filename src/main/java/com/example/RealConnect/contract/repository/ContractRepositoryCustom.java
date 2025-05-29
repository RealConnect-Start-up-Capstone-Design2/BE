package com.example.RealConnect.contract.repository;

import com.example.RealConnect.contract.domain.Contract;
import com.example.RealConnect.user.domain.User;


import java.util.List;

public interface ContractRepositoryCustom {

    // 조건별 조회
    List<Contract> searchContracts(Boolean favorite, String type, String keyword, User user);
}
