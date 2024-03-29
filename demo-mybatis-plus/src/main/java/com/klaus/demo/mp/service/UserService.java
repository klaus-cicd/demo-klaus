package com.klaus.demo.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.klaus.demo.comm.bo.UserBO;
import com.klaus.demo.comm.entity.User;
import com.klaus.demo.mp.vo.PageVO;

import java.util.List;

/**
 * @author Klaus
 */
public interface UserService extends IService<User> {

    /**
     * 根据单个指定条件, 查询单条数据
     *
     * @param userCode userCode
     * @return User
     */
    User getByUserCode(String userCode);


    /**
     * 批量获取
     *
     * @param ids 用户ID列表
     * @return 用户列表
     */
    List<User> batchGetByIdList(List<Long> ids);


    /**
     * 根据对象列表获取指定数据
     *
     * @param userBOList List<UserBO>
     * @return 用户列表
     */
    List<User> batchGetByIdsAndUserCodes(List<UserBO> userBOList);


    /**
     * 根据单个条件 更新数据
     *
     * @param user 待更新的User对象
     */
    void updateByUserCode(User user);


    /**
     * 根据用户列表, 批量更新用户数据
     *
     * @param userList 用户列表
     */
    void batchUpdateById(List<User> userList);

    /**
     * 根据用户code删除指定用户
     *
     * @param userCode 待删除的用户code
     */
    void delByUserCode(String userCode);


    /**
     * 根据指定条件删除
     *
     * @param userList 待删除用户列表
     */
    void batchDel(List<User> userList);

    PageVO<User> page(int page, int size);
}
