package com.demo.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 用户表(User)实体类
 */
@Getter
@Setter
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
}