package com.example.RealConnect.inquiry.domain.dto;

import com.example.RealConnect.inquiry.domain.InquiryStatus;
import com.example.RealConnect.inquiry.domain.InquiryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryUpdateRequest {

    private String name;
    private String phone;
    private String apartmentName;
    private InquiryType type;
    private InquiryStatus status;
    private Long salePrice;
    private Long jeonsePrice;
    private Long monthPrice;
    private String memo;
    private boolean favorite;

}
