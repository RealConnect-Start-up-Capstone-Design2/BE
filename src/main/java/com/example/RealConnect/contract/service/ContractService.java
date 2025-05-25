package com.example.RealConnect.contract.service;

import com.example.RealConnect.contract.domain.*;
import com.example.RealConnect.contract.repository.ContractRepository;
import com.example.RealConnect.inquiry.domain.Inquiry;
import com.example.RealConnect.inquiry.repository.InquiryRepository;
import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.repository.PropertyRepository;
import com.example.RealConnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final PropertyRepository propertyRepository;
    private final InquiryRepository inquiryRepository;


    // 매물관리, 문의관리를 거치지 않고 계약을 바로 작성하는 경우
    public void registerDirectContract(ContractPostRequestDto dto) {
        // 계약 생성
        Contract contract = new Contract();

        contract.setApartment(dto.getApartment());  // 직접 입력받은 아파트명
        contract.setDong(dto.getDong());            // 동
        contract.setHo(dto.getHo());                // 호
        contract.setArea(dto.getArea());            // 면적
        contract.setOwnerName(dto.getOwnerName());  // 소유자 이름
        contract.setOwnerPhone(dto.getOwnerPhone());
        contract.setTenantName(dto.getTenantName()); // 임차인 이름
        contract.setTenantPhone(dto.getTenantPhone());

        contract.setPrice(dto.getContractPrice());  // 계약 금액
        contract.setContractDate(dto.getContractDate().atStartOfDay()); // 계약일
        contract.setExpireDate(dto.getDueDate().atStartOfDay());       // 만기일
        contract.setType(ContractType.valueOf(String.valueOf(dto.getContractType()))); // 계약 유형
        contract.setStatus(ContractStatus.ACTIVE); // 기본은 ACTIVE
        contract.setFavorite(dto.isFavorite());   // 즐겨찾기 여부

        // 계약 저장
        contractRepository.save(contract);
    }

    // 1. 매물 관리에서 -> 계약 등록
    public void registerContractFromProperty(ContractPostRequestDto dto){

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다."));

        User agent = property.getAgent(); // 또는 inquiry.getAgent()

        Contract contract = new Contract();

        contract.setApartment(property.getApartment().getName());
        contract.setDong(property.getApartment().getDong());
        contract.setHo(property.getApartment().getHo());
        contract.setArea(property.getApartment().getArea());
        contract.setOwnerName(property.getOwnerName());
        contract.setOwnerPhone(property.getOwnerPhone());

        contract.setTenantName(dto.getTenantName());
        contract.setTenantPhone(dto.getTenantPhone());
        contract.setPrice(dto.getContractPrice());
        contract.setContractDate(dto.getContractDate().atStartOfDay());
        contract.setExpireDate(dto.getDueDate().atStartOfDay());
        contract.setType(ContractType.valueOf(String.valueOf(dto.getContractType())));
        contract.setStatus(ContractStatus.ACTIVE); // 기본은 ACTIVE
        contract.setFavorite(dto.isFavorite());
        contract.setAgent(agent);

        contractRepository.save(contract);
    }

    // 2. 문의 관리에서 -> 계약등록
    public void registerContractFromInquiry(ContractPostRequestDto dto) {
        // 문의 정보 가져오기
        Inquiry inquiry = inquiryRepository.findById(dto.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 문의를 찾을 수 없습니다."));

        // 중개사 정보
        User agent = inquiry.getAgent(); // 또는 property.getAgent()

        // 계약 생성
        Contract contract = new Contract();
        contract.setApartment(dto.getApartment());
        contract.setDong(dto.getDong());
        contract.setHo(dto.getHo());
        contract.setArea(dto.getArea());
        contract.setOwnerName(dto.getOwnerName());

        contract.setTenantName(inquiry.getName()); // 문의자 이름 매핑
        contract.setTenantPhone(inquiry.getPhone()); // 문의자 연락처 매핑

        contract.setPrice(dto.getContractPrice());
        contract.setContractDate(dto.getContractDate().atStartOfDay());
        contract.setExpireDate(dto.getDueDate().atStartOfDay());
        contract.setType(ContractType.valueOf(String.valueOf(dto.getContractType())));
        contract.setStatus(ContractStatus.ACTIVE); // 기본은 ACTIVE
        contract.setFavorite(dto.isFavorite());
        contract.setAgent(agent);

        // 계약 저장
        contractRepository.save(contract);
    }

    // 2. 조건 기반 계약 검색 - QueryDSL 기반
    public List<ContractResponseDto> searchContracts(Boolean favorite, String type, String keyword){
        List<Contract> contracts = contractRepository.searchContracts(favorite, type, keyword);
        List<ContractResponseDto> dtos = new ArrayList<>();
        for(Contract contract : contracts){
            ContractResponseDto dto = ContractResponseDto.toDto(contract);
            dtos.add(dto);
        }

        return dtos;
    }


    // 계약 삭제
    public void deleteContract(Long contractId){
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("해당 계약을 찾을 수 없습니다."));

        contractRepository.delete(contract);
    }
}
