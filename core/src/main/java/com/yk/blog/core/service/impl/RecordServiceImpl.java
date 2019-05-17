package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.service.RecordService;
import com.yk.blog.data.dao.RecordMapper;
import com.yk.blog.domain.dto.IdLists;
import com.yk.blog.domain.dto.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author yikang
 * @date 2019/5/15
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    RecordMapper recordMapper;

    @Override
    public Record getRecord(String userId, int blogId) {
        return recordMapper.getRecord(userId, blogId);
    }

    @Override
    public void insertRecord(String userId, int blogId, int score) {
        Record oldRecord = getRecord(userId,blogId);
        Record record = new Record();
        record.setBlogId(blogId);
        record.setUserId(userId);
        if (oldRecord == null) {
            record.setScore(score);
            recordMapper.insertRecord(record);
        }else{
            if(oldRecord.getScore() < score){
                record.setScore(score);
                recordMapper.updataRecord(record);
            }

        }
    }

    @Override
    public List<Record> getRecords(List<String> userIds, List<Integer> blogIds) {
        IdLists idLists = new IdLists();
        idLists.setUserIds(userIds);
        idLists.setBlogIds(blogIds);
        return recordMapper.getRecords(idLists);
    }
}
