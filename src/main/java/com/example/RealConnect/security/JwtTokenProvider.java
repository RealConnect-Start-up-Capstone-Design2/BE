package com.example.RealConnect.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public Long getUserId(String token) {
        // JWT 디코딩 로직 또는 예시:
        return 1L; // ← 테스트용 고정값 (실제로는 파싱해서 반환해야 함)
    }
}
