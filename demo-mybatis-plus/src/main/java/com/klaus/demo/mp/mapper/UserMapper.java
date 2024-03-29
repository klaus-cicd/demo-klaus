package com.klaus.demo.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klaus.demo.comm.bo.UserBO;
import com.klaus.demo.comm.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Klaus
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    List<User> batchGetByIdsAndUserCodes(List<UserBO> userBOList);

}
