package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.PropertyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyStatusDto {

    @NotNull(message = "상태를 작성하세요.")
    private int status;
}
