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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final PropertyRepository propertyRepository;
    private final InquiryRepository inquiryRepository;

    // 계약 등록
    public void registerContract(ContractPostRequestDto dto){

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다."));

        Inquiry inquiry = inquiryRepository.findById(dto.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 문의를 찾을 수 없습니다."));

        User agent = property.getAgent(); // 또는 inquiry.getAgent()

        Contract contract = new Contract();
        contract.setApartment(property.getApartment().getName());
        contract.setDong(property.getApartment().getDong());
        contract.setHo(property.getApartment().getHo());
        contract.setArea(property.getApartment().getArea());
        contract.setOwnerName(property.getOwnerName());

        contract.setTenantName(dto.getTenantName());
        contract.setPrice(dto.getContractPrice());
        contract.setContractDate(dto.getContractDate().atStartOfDay());
        contract.setExpireDate(dto.getDueDate().atStartOfDay());
        contract.setType(ContractType.valueOf(String.valueOf(dto.getContractType())));
        contract.setStatus(ContractStatus.ACTIVE); // 기본은 ACTIVE
        contract.setFavorite(dto.isFavorite());
        contract.setAgent(agent);

        contractRepository.save(contract);
    }

    // 조건 기반 계약 검색 - QueryDSL 기반
    public List<ContractResponseDto> searchContracts(Boolean favorite, String type, String keyword){

    }
    //
}
