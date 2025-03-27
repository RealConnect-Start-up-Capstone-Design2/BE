package com.example.RealConnect.user.controller;

import com.example.RealConnect.user.domain.dto.UserRegisterDto;
import com.example.RealConnect.user.exception.PasswordNotMatchException;
import com.example.RealConnect.user.exception.UsernameAlreadyExistsException;
import com.example.RealConnect.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param userRegisterDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }

        userService.register(userRegisterDto);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @PostMapping


    /**
     * 중복 아이디 존재,
     * 비밀번호와 비밀번호확인이 다른 경우
     * @param error
     * @return 400
     */
    @ExceptionHandler({UsernameAlreadyExistsException.class, PasswordNotMatchException.class})
    public ResponseEntity<String> handleUsernameAlreadyExists(RuntimeException error) {
        return ResponseEntity
                .badRequest()
                .body("ERROR: " + error.getMessage());
    }
}
