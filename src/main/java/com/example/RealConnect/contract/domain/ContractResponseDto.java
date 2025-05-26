package com.example.RealConnect.contract.domain;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDto {
    // 계약 조회 시 사용
    private Long id;
    private String apartment; // property 엔티티에서 가져옴
    private String dong; // property 엔티티에서 가져옴
    private String ho; // property 엔티티에서 가져옴
    private String area; // property 엔티티에서 가져옴

    private String ownerName; // Property.owner로 매핑
    private String ownerPhone;

    private String tenantName;
    private String tenantPhone;

    private ContractType contractType;
    private String contractPrice; // 월세인 경우를 고려하여 String형으로 변환

    private LocalDate contractDate;
    private LocalDate dueDate;

    private ContractStatus contractStatus;
    private boolean favorite;

    // toDTO
    public static ContractResponseDto toDto(Contract contract) {
        ContractResponseDto dto = new ContractResponseDto();

        // Contract 엔티티에서 필드 값을 가져와서 DTO에 설정
        dto.setId(contract.getId());
        dto.setApartment(contract.getApartment());
        dto.setDong(contract.getDong());
        dto.setHo(contract.getHo());
        dto.setArea(contract.getArea());

        dto.setOwnerName(contract.getOwnerName());
        dto.setOwnerPhone(contract.getOwnerPhone());

        dto.setTenantName(contract.getTenantName());
        dto.setTenantPhone(contract.getTenantPhone());

        dto.setContractType(contract.getType());
        dto.setContractPrice(contract.getPrice());

        dto.setContractDate(contract.getContractDate().toLocalDate());
        dto.setDueDate(contract.getExpireDate().toLocalDate());

        dto.setContractStatus(contract.getStatus());
        dto.setFavorite(contract.isFavorite());

        return dto;
    }
}
