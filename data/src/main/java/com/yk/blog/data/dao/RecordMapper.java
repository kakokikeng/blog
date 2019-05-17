package com.yk.blog.data.dao;

import com.yk.blog.domain.dto.IdLists;
import com.yk.blog.domain.dto.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper {

    List<Record> getRecords(@Param("idLists")IdLists idLists);

    int updataRecord(@Param("record") Record record);

    int insertRecord(@Param("record") Record record);

    Record getRecord(@Param("userId") String userId, @Param("blogId") int blogId);

}
