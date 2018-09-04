package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.GenericResult;
import com.t4f.gaea.dto.Result;
import com.yk.blog.core.dto.UserRespDTO;
import com.yk.blog.core.service.CountService;
import com.yk.blog.core.service.FollowerService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.ErrorMessages;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.core.utils.UserUtils;
import com.yk.blog.data.dao.FollowerMapper;
import com.yk.blog.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.yk.blog.core.utils.GenericResultUtils.generateResultWithCount;
import static com.yk.blog.core.utils.UserUtils.getIdList;

/**
 * @author yikang
 * @date 2018/9/3
 */
@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    FollowerMapper followerMapper;

    @Autowired
    UserService userService;

    @Autowired
    CountService countService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Result follow(String followId, String followedId) {
        if (!userService.existUsers(getIdList(followedId, followId))) {
            return GenericResultUtils.genericNormalResult(Boolean.FALSE, ErrorMessages.WRONG_USER_ID.message);
        }
        int count = followerMapper.insertFollowRecord(followId, followedId);
        if (count > 0) {
            if (countService.increaseFans(followedId) > 0) {
                return generateResultWithCount(count);
            } else {
                throw new RuntimeException(ErrorMessages.ERROR_INCREASE_FANS.message);
            }
        }
        return generateResultWithCount(count);
    }

    @Override
    public Result unfollow(String followId, String followedId) {
        if (!userService.existUsers(getIdList(followedId, followId))) {
            return GenericResultUtils.genericNormalResult(Boolean.FALSE, ErrorMessages.WRONG_USER_ID.message);
        }
        Integer recordId = followerMapper.getIdByFollowAndFollower(followId, followedId);
        if (recordId != null) {
            int count = followerMapper.deleteRecord(recordId);
            return generateResultWithCount(count);
        } else {
            return GenericResultUtils.genericNormalResult(Boolean.FALSE, ErrorMessages.FOLLOW_RECORD_NOT_EXIST.message);
        }
    }

    @Override
    public GenericResult<List<UserRespDTO>> getFollowers(String userId) {
        if (!userService.existUser(userId)) {
            return GenericResultUtils.genericFailureResult(ErrorMessages.WRONG_USER_ID.message);
        }
        List<UserRespDTO> data = new ArrayList<>();
        List<String> userIdList = followerMapper.getFollowers(userId);
        if(userIdList != null){
            List<User> userList = userService.getUserListByIdList(userIdList);
            data = UserUtils.changeUserListToDTOList(userList);
        }
        return GenericResultUtils.genericResult(Boolean.TRUE,data);
    }

    @Override
    public GenericResult<List<UserRespDTO>> getFolloweders(String userId) {
        if (!userService.existUser(userId)) {
            return GenericResultUtils.genericFailureResult(ErrorMessages.WRONG_USER_ID.message);
        }
        List<UserRespDTO> data = new ArrayList<>();
        List<String> userIdList = followerMapper.getFolloweders(userId);
        if(userIdList != null){
            List<User> userList = userService.getUserListByIdList(userIdList);
            data = UserUtils.changeUserListToDTOList(userList);
        }
        return GenericResultUtils.genericResult(Boolean.TRUE,data);
    }
}
