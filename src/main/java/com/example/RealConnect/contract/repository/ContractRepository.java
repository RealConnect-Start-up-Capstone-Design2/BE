package com.example.RealConnect.contract.repository;

import com.example.RealConnect.contract.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long>, ContractRepositoryCustom {

    //Optional<Contract> findById(Long contractId);

// QueryDSL로
}
