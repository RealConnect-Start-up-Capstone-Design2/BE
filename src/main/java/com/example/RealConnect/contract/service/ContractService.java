package com.example.RealConnect.contract.service;

import com.example.RealConnect.contract.Exception.ContractNotFoundException;
import com.example.RealConnect.contract.domain.*;
import com.example.RealConnect.contract.repository.ContractRepository;
import com.example.RealConnect.contract.Exception.ContractNotMatchException;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;


    // 계약 생성(등록)
    @Transactional
    public ContractResponseDto createContract(ContractPostRequestDto dto, String username) {

        User user = userRepository.findByUsername(username).get();

        // 계약 생성
        Contract contract = Contract.builder()
                .apartment(dto.getApartment())
                .dong(dto.getDong())
                .ho(dto.getHo())
                .area(dto.getArea())
                .ownerName(dto.getOwnerName())
                .ownerPhone(dto.getOwnerPhone())
                .tenantName(dto.getTenantName())
                .tenantPhone(dto.getTenantPhone())
                .price(dto.getContractPrice())
                .contractDate(dto.getContractDate())
                .expireDate(dto.getDueDate())
                .type(dto.getContractType())
                .status(dto.getContractStatus())
                .favorite(dto.isFavorite())
                .agent(user)
                .build();

        // 계약 저장
        contract = contractRepository.save(contract);
        return ContractResponseDto.toDto(contract);
    }

    // 2. 조건 기반 계약 검색 - QueryDSL 기반
    @Transactional(readOnly = true)
    public List<ContractResponseDto> searchContracts(Boolean favorite, String type, String keyword, String username){

        User user = userRepository.findByUsername(username).get();

        List<Contract> contracts = contractRepository.searchContracts(favorite, type, keyword, user);

        List<ContractResponseDto> dtos = new ArrayList<>();
        for(Contract contract : contracts){
            ContractResponseDto dto = ContractResponseDto.toDto(contract);
            dtos.add(dto);
        }

        return dtos;
    }

    // 계약 수정 - 계약 수정에 대한 권한이 있는지 검증 후 계약 수정
    @Transactional
    public ContractResponseDto updateContract(Long contractId, ContractPostRequestDto dto, String username) {
        // 계약 정보 가져오기
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException("해당 계약을 찾을 수 없습니다.")); // 계약이 존재하지 않으면 예외 처리

        User user = userRepository.findByUsername(username).get();

        // 계약 수정 권한 확인
        verifyContractOwner(contract, user);  // 계약 수정 권한 검증

        contract.update(dto);

        return ContractResponseDto.toDto(contract);
    }


    // 계약 삭제
    @Transactional
    public void deleteContract(Long contractId,String username){
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException("해당 계약을 찾을 수 없습니다."));

        User user = userRepository.findByUsername(username).get();

        verifyContractOwner(contract, user);

        contractRepository.delete(contract);
    }

    /**
     * 계약 수정 권한 확인
     * @param contract
     * @param user
     */
    private void verifyContractOwner(Contract contract, User user) {
        // 계약의 중개사(또는 작성자)만 수정 가능
        if (!contract.getAgent().equals(user)) {
            throw new ContractNotMatchException("계약 수정 권한이 없습니다.");
        }
    }

}
