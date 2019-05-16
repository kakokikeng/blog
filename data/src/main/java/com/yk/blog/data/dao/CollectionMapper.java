package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionMapper {

    int createCollection(@Param("collection") Collection collection);

    int cancelCollect(@Param("userId") String userId, @Param("blogId") int blogId);

    Collection getCollection(@Param("userId") String userId,@Param("blogId") int blogId);

}
