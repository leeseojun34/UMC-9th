package com.example.umc9th.domain.user.service;

import com.example.umc9th.domain.user.converter.UserConverter;
import com.example.umc9th.domain.user.dto.UserRequestDTO;
import com.example.umc9th.domain.user.dto.UserResponseDTO;
import com.example.umc9th.domain.user.entity.User;
import com.example.umc9th.domain.user.enums.Role;
import com.example.umc9th.domain.user.exception.UserErrorCode;
import com.example.umc9th.domain.user.repository.UserRepository;
import com.example.umc9th.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final com.example.umc9th.global.jwt.JwtUtil jwtUtil;

    @Transactional
    public UserResponseDTO.JoinResultDTO signup(UserRequestDTO.JoinDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = UserConverter.toUser(dto, encodedPassword, Role.ROLE_USER);
        userRepository.save(user);
        return UserConverter.toJoinResultDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO.LoginDTO login(UserRequestDTO.LoginDTO dto) {
        User user = userRepository.findByEmail(dto.email())
            .orElseThrow(() -> new GeneralException(
                UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new GeneralException(
                UserErrorCode.PASSWORD_NOT_MATCH);
        }

        CustomUserDetails userDetails = new CustomUserDetails(
            user);
        String accessToken = jwtUtil.createAccessToken(userDetails);

        return UserConverter.toLoginDTO(user, accessToken);
    }

    @Transactional
    public void withdrawUser(Long userId) {
        log.info("회원 탈퇴 시작: userId = {}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException(UserErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);

        log.info("회원 탈퇴 완료: userId = {}", userId);
    }

    public User getMyPageInfo(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException(UserErrorCode.USER_NOT_FOUND));
    }
}
