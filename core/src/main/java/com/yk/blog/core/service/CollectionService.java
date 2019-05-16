package com.yk.blog.core.service;

import com.yk.blog.domain.dto.Collection;


public interface CollectionService {

    /**
     * 新建收藏记录
     *
     * @param
     * @return int
     * @author yikang
     * @date 2019/5/16
     */
    int createCollection(String userId, int blogId);

    /**
     * 通过userId和blogId获取记录
     *
     * @param
     * @return
     * @author yikang
     * @date 2019/5/16
     */
    Collection getCollection(String userId, int blogId);

}
