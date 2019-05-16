package com.yk.blog.core.service.impl;

import com.yk.blog.core.constant.Constant;
import com.yk.blog.core.service.CollectionService;
import com.yk.blog.core.service.RecordService;
import com.yk.blog.data.dao.CollectionMapper;
import com.yk.blog.domain.dto.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yikang
 * @date 2019/5/16
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    RecordService recordService;

    @Override
    public int createCollection(String userId, int blogId) {
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setBlogId(blogId);
        Collection ifExist = collectionMapper.getCollection(userId, blogId);
        if (ifExist == null) {
            recordService.insertRecord(userId,blogId, Constant.SCORE_COLLECTION_);
            return collectionMapper.createCollection(collection);
        }
        return 0;
    }

    @Override
    public int cancelCollect(String userId, int blogId) {
        return collectionMapper.cancelCollect(userId, blogId);
    }

    @Override
    public Collection getCollection(String userId, int blogId) {
        return collectionMapper.getCollection(userId, blogId);
    }

    @Override
    public List<Collection> getCollection(String userId) {
        return collectionMapper.getCollectionByUserId(userId);
    }
}
