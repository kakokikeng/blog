package com.yk.blog.core.service.impl;

import com.t4f.gaea.dto.Result;
import com.yk.blog.core.service.CountService;
import com.yk.blog.core.service.UserService;
import com.yk.blog.core.utils.GenericResultUtils;
import com.yk.blog.data.dao.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yk.blog.core.utils.UserUtils.wrongUserIdResult;

/**
 * @author yikang
 * @date 2018/9/4
 */
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserService userService;

    @Override
    public Result increaseLikeCount(String userId, int blogId) {
        if (!userService.existUser(userId)) {
            return wrongUserIdResult();
        }
        int count = blogMapper.increaseLikeCount(userId, blogId);
        if (count > 0) {
            return GenericResultUtils.genericNormalResult(true);
        } else {
            return GenericResultUtils.genericNormalResult(false);
        }
    }

    @Override
    public Result increaseReadCount(int blogId) {
        return null;
    }

    @Override
    public int getReadCount(int blogId) {
        return 0;
    }
}
