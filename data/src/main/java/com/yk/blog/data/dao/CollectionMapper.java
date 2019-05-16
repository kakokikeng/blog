package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionMapper {

    int createCollection(Collection collection);

    Collection getCollection(@Param("userId") String userId,@Param("blogId") int blogId);

}
