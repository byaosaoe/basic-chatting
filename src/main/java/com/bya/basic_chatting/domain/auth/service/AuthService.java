package com.bya.basic_chatting.domain.auth.service;

import com.bya.basic_chatting.common.exception.CustomException;
import com.bya.basic_chatting.common.exception.ErrorCode;
import com.bya.basic_chatting.domain.auth.model.request.CreateUserRequest;
import com.bya.basic_chatting.domain.auth.model.request.LoginRequest;
import com.bya.basic_chatting.domain.auth.model.response.CreateUserResponse;
import com.bya.basic_chatting.domain.auth.model.response.LoginResponse;
import com.bya.basic_chatting.domain.repository.UserRepository;
import com.bya.basic_chatting.domain.repository.entity.User;
import com.bya.basic_chatting.domain.repository.entity.UserCredentials;
import com.bya.basic_chatting.security.Hasher;
import com.bya.basic_chatting.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final Hasher hasher;

    @Transactional(transactionManager = "createUserTransactionManager")
    public CreateUserResponse createUser(CreateUserRequest request) {
        Optional<User> user = userRepository.findByName(request.name());

        if (user.isPresent()) {
            // TODO 에러
            log.error("USER_ALREADY_EXISTS: {}", request.name());
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        try {
            User newUser = this.newUser(request.name());
            UserCredentials newCredentials = this.newUserCredentials(request.password(), newUser);
            newUser.setCredentials(newCredentials);

            User savedUser = userRepository.save(newUser);

            if (savedUser == null) {
                // TODO 에러 처리
                throw new CustomException(ErrorCode.USER_SAVED_FAILED);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.USER_SAVED_FAILED);
        }

        return new CreateUserResponse(request.name());
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByName(request.name())
                .orElseThrow(()-> {
                    log.error("NOT_EXIST_USER: {}", request.name());
                    throw new CustomException(ErrorCode.NOT_EXIST_USER);
                });

        if (!user.getUserCredentials().getHashed_password().equals(hasher.getHashingValue(request.password()))) {
            System.out.println(user.getUserCredentials().getHashed_password());
            throw new CustomException(ErrorCode.MIS_MATCH_PASSWORD);
        }

        // TODO JWT
        String token = JWTProvider.createToken(request.name());
        return new LoginResponse(ErrorCode.SUCCESS, token);
    }

    public String getUserFromToken(String token) {
        return JWTProvider.getUserFromToken(token);
    }

    private User newUser(String name) {
        User newUser = User.builder()
                .name(name)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();

        return newUser;
    }

    private UserCredentials newUserCredentials(String password, User user) {
        // TODO hash

        String hashedValue = hasher.getHashingValue(password);

        UserCredentials cre = UserCredentials.builder()
                .user(user)
                .hashed_password(hashedValue)
                .build();

        return cre;
    }
}
