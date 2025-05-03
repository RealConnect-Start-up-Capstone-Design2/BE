package com.example.RealConnect.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String auth = request.getHeader("Authorization");

        // 토큰이 없으면 다음 필터로 넘김 (permitAll 경로에서도 작동해야 하므로) - 테스트 용/*if (auth == null || !auth.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        */

        //토큰이 없는경우
        if (auth == null || !auth.startsWith("Bearer "))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //토큰없는경우 401반환
            response.getWriter().write("토큰이 없습니다.");
            return;
        }

        String token = auth.split(" ")[1]; //Bearer 부분 제거

        try
        {
            //만료된 경우
            if (jwtUtil.isExpired(token)) //만료시 예외를 던짐
            {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //만료된 경우 401반환
                response.getWriter().write("토큰 만료");
                return;
            }
            //토큰에서 id role가져옴
            String username = jwtUtil.getUsernameFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            List<GrantedAuthority> authorities = new ArrayList<>();


            authorities.add(new SimpleGrantedAuthority(role));

            User user = new User(username, "1", authorities);

            //시큐리티 인증토큰
            Authentication authToekn = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            //서버에 사용자등록(요청기간 동안에만 유효)
            SecurityContextHolder.getContext().setAuthentication(authToekn);

            filterChain.doFilter(request, response);


        }catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //만료된 경우 401반환
            response.getWriter().write("Token expired");
        }catch(JwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
        }catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        // 필터를 거치지 않을 경로 리스트
        List<String> excludePaths = List.of(
                "/login",
                "/",
                "/api/register",
                "/api/refresh-token"
        );

        // 경로가 하나라도 매칭되면 필터를 거치지 않음
        return excludePaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}