package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecordMapper {

    int updataRecord(Record record);

    int insertRecord(Record record);

    Record getRecord(@Param("userId") String userId, @Param("blogId") int blogId);

}