package com.example.RealConnect.user.controller;

import com.example.RealConnect.security.jwt.JWTUtil;
import com.example.RealConnect.user.domain.dto.UserRegisterDto;
import com.example.RealConnect.user.exception.PasswordNotMatchException;
import com.example.RealConnect.user.exception.UsernameAlreadyExistsException;
import com.example.RealConnect.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

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

    @GetMapping("/api/test")
    public ResponseEntity<String> test(Principal principal)
    {
        System.out.println(principal.getName());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/api/refresh-token")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if(refreshToken == null) //없거나, 유효하지 않다면, 401줘서 다시 로그인 할 수 있도록.
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰 없음");
        }
        try{
            jwtUtil.isExpired(refreshToken);
        }catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("만료된 리프레시 토큰");
        }catch(JwtException | IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 리프레시 토큰");
        }

        // 새 액세스 토큰 발급
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String role = jwtUtil.getRoleFromToken(refreshToken);
        String newAccessToken = jwtUtil.generateJwt(username, role,1000 * 60 * 10L);//10분

        // 응답에 새 액세스 토큰 추가
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        return ResponseEntity.ok("토큰 갱신완료");
    }

    /**
     * 중복 아이디 존재,
     * @param error
     * @return 409
     */
    @ExceptionHandler({UsernameAlreadyExistsException.class})
    public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException error) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error.getMessage());
    }

    /**
     * 비밀번호와 비밀번호확인이 다른 경우
     * @param error
     * @return 400
     */
    @ExceptionHandler({PasswordNotMatchException.class})
    public ResponseEntity<String> handlePasswordNotMatch(PasswordNotMatchException error) {
        return ResponseEntity
                .badRequest()
                .body(error.getMessage());
    }
}
