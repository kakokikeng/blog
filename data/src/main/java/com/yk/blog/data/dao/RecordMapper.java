package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecordMapper {

    int updataRecord(@Param("record") Record record);

    int insertRecord(@Param("record") Record record);

    Record getRecord(@Param("userId") String userId, @Param("blogId") int blogId);

}
