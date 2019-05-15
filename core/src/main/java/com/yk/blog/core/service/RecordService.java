package com.yk.blog.core.service;

import com.yk.blog.domain.dto.Record;

public interface RecordService {

    /**
     * 通过用户id和文章id获取指定记录
     *
     * @param
     * @return
     * @author yikang
     * @date 2019/5/15
     */
    Record getRecord(String userId, int blogId);

    /**
     * @param
     * @return
     * @author yikang
     * @date 2019/5/15
     */
    void insertRecord(String userId, int blogId, int score);

}
