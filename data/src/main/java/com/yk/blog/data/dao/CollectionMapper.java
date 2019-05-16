package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {

    List<Collection> getCollectionByUserId(@Param("userId")String userId);

    int createCollection(@Param("collection") Collection collection);

    int cancelCollect(@Param("userId") String userId, @Param("blogId") int blogId);

    Collection getCollection(@Param("userId") String userId,@Param("blogId") int blogId);

}
