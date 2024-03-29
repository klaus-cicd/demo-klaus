package com.klaus.demo.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.klaus.demo.comm.bo.UserBO;
import com.klaus.demo.comm.entity.User;
import com.klaus.demo.mp.mapper.UserMapper;
import com.klaus.demo.mp.service.UserService;
import com.klaus.demo.mp.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Klaus
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public User getByUserCode(String userCode) {
        return baseMapper.selectOne(lambdaQuery().eq(User::getUserCode, userCode));
    }

    @Override
    public List<User> batchGetByIdList(List<Long> ids) {
        return baseMapper.selectBatchIds(ids);
    }

    @Override
    public List<User> batchGetByIdsAndUserCodes(List<UserBO> userBOList) {
        return baseMapper.batchGetByIdsAndUserCodes(userBOList);
    }

    @Override
    public void updateByUserCode(User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        userLambdaQueryWrapper.select().eq(User::getUserCode, "123");
    }

    @Override
    public void batchUpdateById(List<User> userList) {

    }

    @Override
    public void delByUserCode(String userCode) {

    }

    @Override
    public void batchDel(List<User> userList) {

    }

    @Override
    public PageVO<User> page(int pageNo, int size) {
        Page<User> result = page(Page.of(pageNo, size), Wrappers.lambdaQuery());
        return PageVO.fromPage(result);
    }
}
