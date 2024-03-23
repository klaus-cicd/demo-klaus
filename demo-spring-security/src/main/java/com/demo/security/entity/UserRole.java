package com.demo.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_role")
public class UserRole {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private int userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private int roleId;

    /**
     * 角色类型
     */
    @TableField("role_type")
    private String roleType;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
