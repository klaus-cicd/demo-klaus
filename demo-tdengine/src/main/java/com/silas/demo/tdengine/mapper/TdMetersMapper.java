package com.silas.demo.tdengine.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.silas.demo.tdengine.entity.TdMeters;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Klaus
 */
@Mapper
public interface TdMetersMapper extends MPJBaseMapper<TdMeters> {

    /**
     * 新增
     *
     * @param tdMeters 待新增对象
     * @return 新增结果
     */
    int tdSave(TdMeters tdMeters);
}
