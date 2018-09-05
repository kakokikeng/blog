package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.UserReqDTO;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.UserUtils;
import com.yk.blog.data.dao.UserMapper;
import com.yk.blog.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean existUser(String userId) {
        if (userId.isEmpty() || userMapper.existUser(userId) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean existUsers(List<String> userIds) {
        if (userIds == null || userIds.size() == 0) {
            return true;
        }
        Set<String> set = new HashSet<>();
        for (String userId : userIds) {
            set.add(userId);
        }
        List<String> list = userMapper.existUsers(userIds);
        if (list.size() != set.size()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public GenericResult<List<UserRespDTO>> getUsers(List<String> userIds) {
        return GenericResultUtils.genericResult(true, getUserListByIdList(userIds));
    }

    @Override
    public List<UserRespDTO> getUserListByIdList(List<String> userIds) {
        List<UserRespDTO> result = new ArrayList<>();
        if (userIds == null || userIds.size() == 0) {
            return result;
        }
        List<User> list = userMapper.getUserListByIdList(userIds);
        result = UserUtils.changeUserListToDTOList(list);
        return result;
    }

    @Override
    public Result updateUser(String userId, UserReqDTO userReqDTO) {
        User user = userReqDTO.changeToUser(userId);
        int count = userMapper.updateUser(userId, user);
        return GenericResultUtils.generateResultWithCount(count);
    }

    @Override
    public Result deleteUser(String userId) {
        int count = userMapper.deleteUser(userId);
        return GenericResultUtils.generateResultWithCount(count);
    }

    @Override
    public Result createUser(UserReqDTO userReqDTO) {
        User user = userReqDTO.changeToUser();
        int count = userMapper.insertUser(user);
        return GenericResultUtils.generateResultWithCount(count);
    }
}
