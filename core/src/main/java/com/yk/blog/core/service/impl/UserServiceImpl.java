package com.yk.blog.core.service.impl;

import com.yk.blog.core.service.UserService;
import com.yk.blog.domain.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean existUser(String userId) {
        return false;
    }

    @Override
    public boolean existUsers(List<String> userIds) {
        return false;
    }

    @Override
    public List<User> getUserListByIdList(List<String> userIds) {
        return null;
    }
}
