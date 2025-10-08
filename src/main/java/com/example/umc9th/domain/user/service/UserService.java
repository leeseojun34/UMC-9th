package com.example.umc9th.domain.user.service;

import com.example.umc9th.domain.user.entity.User;
import com.example.umc9th.domain.user.exception.UserErrorCode;
import com.example.umc9th.domain.user.repository.UserRepository;
import com.example.umc9th.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void withdrawUser(Long userId) {
        log.info("회원 탈퇴 시작: userId = {}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException(UserErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);

        log.info("회원 탈퇴 완료: userId = {}", userId);
    }
}
