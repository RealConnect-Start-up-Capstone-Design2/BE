package com.example.RealConnect.user.service;

import com.example.RealConnect.user.domain.Role;
import com.example.RealConnect.user.domain.User;
import com.example.RealConnect.user.exception.PasswordNotMatchException;
import com.example.RealConnect.user.exception.UsernameAlreadyExistsException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.RealConnect.user.repository.UserRepository;
import  com.example.RealConnect.user.domain.dto.UserRegisterDto;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public boolean register(UserRegisterDto userRegisterDto)
    {
        usernameVerify(userRegisterDto.getUsername());
        passwordVerify(userRegisterDto.getPassword(), userRegisterDto.getPasswordVerify());

        User user = User.builder()
                .username(userRegisterDto.getUsername())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .name(userRegisterDto.getName())
                .role(Role.BASIC)
                .build();
        userRepository.save(user);
        return true;
    }

    private void passwordVerify(String password, String passwordVerify)
    {
        if(!password.equals(passwordVerify)) throw new PasswordNotMatchException("비밀번호 확인이 다릅니다.");
    }

    /**
     * username이 존재하면 예외 발생
     * @param username
     */
    private void usernameVerify(String username)
    {
        if(userRepository.existsByUsername(username))
        {
            throw new UsernameAlreadyExistsException("이미 존재하는 아이디");
        }
    }
}
