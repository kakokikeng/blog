package com.yk.blog.core.service.impl;

import com.yk.blog.core.service.UserService;
import org.springframework.stereotype.Service;

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

}
