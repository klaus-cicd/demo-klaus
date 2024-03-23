package com.demo.security.vo;


import com.klaus.demo.comm.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseEntity {


    @Length(min = 5, max = 10, message = "用户名长度为5至10位")
    private String username;

    @Length(min = 6, max = 20, message = "用户名长度为6至20位")
    private String pwd;

    @NotBlank(message = "请输入昵称")
    private String nickname;

}
