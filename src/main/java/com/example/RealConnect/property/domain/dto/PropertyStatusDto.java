package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.PropertyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PropertyStatusDto {
    @NotNull(message = "매물 아이디를 작성하세요.")
    private Long id;
    @NotNull(message = "상태를 작성하세요.")
    private PropertyStatus Status;
}
