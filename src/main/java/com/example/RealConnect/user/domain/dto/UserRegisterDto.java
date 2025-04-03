package com.example.RealConnect.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {

    @NotBlank(message = "아이디를 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력하세요.")
    private String passwordVerify;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;
}