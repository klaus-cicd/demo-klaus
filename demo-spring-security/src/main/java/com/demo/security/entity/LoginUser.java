package com.demo.security.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.demo.security.enums.EnumRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    private List<String> roleTypes;

    /**
     * 为了避免每次获取权限时都重新遍历permissions来创建List<SimpleGrantedAuthority>, 直接将其提到成员变量
     * 由于SimpleGrantedAuthority对象不会被Redis序列化, 故LoginUser存入Redis时忽略该字段
     */
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user, List<String> roleTypes) {
        this.user = user;
        this.roleTypes = roleTypes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null != this.authorities) {
            return this.authorities;
        }

        if (CollectionUtils.isEmpty(this.roleTypes)) {
            // 默认为买家权限
            return Collections.singletonList(new SimpleGrantedAuthority(EnumRole.BUYER.type()));
        }

        this.authorities = this.roleTypes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getDelFlag() == 0;
    }

}