package com.example.RealConnect.contract.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class ContractPostRequestDto {

    // 계약 등록 시 사용 - 단지명, 동, 호수, 소유주 이름 - property에서 매핑됨

    private Long propertyId;        // 매물 ID (연관)
    private Long inquiryId;         // 문의 ID (연관)

    private String tenantName;      // 임차인
    private String tenantPhone;     // 임차인 연락처

    private ContractType contractType;    // BUY / JEONSE / MONTH_RENT
    private Long contractPrice;     // 매매가 / 전세가 / 월세 (선택적)

    private LocalDate contractDate;  // 계약일
    private LocalDate dueDate;       // 만기일
    private boolean favorite;       // 즐겨찾기 여부

    // 계액 등록 시 공인중개사가 직접 입력; 문의 관리에서 계약으로 넘어오는 경우에만 사용
    private String apartment;
    private String Dong;
    private String Ho;
    private String area;
    private String ownerName;
}
