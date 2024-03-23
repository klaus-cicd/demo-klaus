package com.silas.demo.tdengine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.silas.demo.tdengine.entity.Test;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Klaus
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {

    /**
     * 新增数据同时懒加载的方式创建对应子表
     * @param childTableName 子表名称
     * @param tags
     * @param values
     * @return
     */
    @Insert("INSERT INTO ${childTableName} USING ${tableName} TAGS (${tags}) VALUES (${values});")
    int customInsert(@Param("childTableName") String childTableName,
                     @Param("tags") String tags, @Param("values") String values);
}
