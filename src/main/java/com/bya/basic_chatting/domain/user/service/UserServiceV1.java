package com.bya.basic_chatting.domain.user.service;

import com.bya.basic_chatting.common.exception.ErrorCode;
import com.bya.basic_chatting.domain.repository.UserRepository;
import com.bya.basic_chatting.domain.user.model.response.UserSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceV1 {

    private final UserRepository userRepository;

    public UserSearchResponse searchUser(String name, String user) {
        List<String> names = userRepository.findNameByNameMatch(name, user);

        return new UserSearchResponse(ErrorCode.SUCCESS, names);
    }
}
