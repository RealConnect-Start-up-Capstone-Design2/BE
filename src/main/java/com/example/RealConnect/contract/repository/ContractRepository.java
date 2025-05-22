package com.example.RealConnect.contract.repository;

import com.example.RealConnect.contract.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long>, ContractRepositoryCustom {

// QueryDSL로
}
