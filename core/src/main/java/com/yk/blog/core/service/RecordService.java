package com.yk.blog.core.service;

import com.yk.blog.domain.dto.Record;

import java.util.List;

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
     *  通过userId list和blogId list获取记录列表
     *  @author  yikang
     *  @date  2019/5/17
     *  @param
     *  @return
     */
    List<Record> getRecords(List<String> userIds,List<Integer> blogIds);

    /**
     * @param
     * @return
     * @author yikang
     * @date 2019/5/15
     */
    void insertRecord(String userId, int blogId, int score);

}
