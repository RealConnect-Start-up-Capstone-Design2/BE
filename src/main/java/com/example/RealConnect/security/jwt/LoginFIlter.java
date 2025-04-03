package com.example.RealConnect.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class LoginFIlter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        if(request.getContentType().equals("application/json"))
        {
            try{
                Map credentials = objectMapper.readValue(request.getInputStream(),Map.class);
                String username = credentials.get("username").toString();
                String password = credentials.get("password").toString();

                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);

                return authenticationManager.authenticate(authRequest);
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        //x-www-form-urlencoded
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
    {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String username = userDetails.getUsername();
        // 하나만 꺼내기 (권한이 하나라고 가정)
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        System.out.println("role:::   ="+role);

        //액세스 토큰 발급(10분)
        String token = jwtUtil.generateJwt(username,role, 1000*60*10L); //10분

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Authorization", "Bearer " + token);

        // 리프레시 토큰 발급 (7일 유효)
        String refreshToken = jwtUtil.generateJwt(username, role,1000 * 60 * 60 * 24 * 7L);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true); // JavaScript 접근 금지
        refreshCookie.setSecure(true);  // HTTPS에서만 전송
        refreshCookie.setPath("/");     // 쿠키 경로 설정
        refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일 유효
        response.addCookie(refreshCookie);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
    {
        if(failed instanceof BadCredentialsException)//401
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else if(failed instanceof InternalAuthenticationServiceException)//서버오류
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//그외
        }
    }
}
