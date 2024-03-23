package com.demo.security.config;

import com.klaus.demo.comm.anno.NoAuthRequired;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class CustomAccessDecisionManager extends AbstractAccessDecisionManager {

    public CustomAccessDecisionManager(List<AccessDecisionVoter<?>> decisionVoters) {
        super(decisionVoters);
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AuthenticationCredentialsNotFoundException {
        boolean authenticated = authentication != null && authentication.isAuthenticated();
        if (!authenticated) {
            throw new AuthenticationCredentialsNotFoundException("拒绝访问");
        }

        if (object instanceof MethodInvocation) {
            MethodInvocation methodInvocation = (MethodInvocation) object;
            Method method = methodInvocation.getMethod();

            if (method.isAnnotationPresent(NoAuthRequired.class)) {
                return; // 绕过身份验证
            }
        } else if (object instanceof Class) {
            Class<?> targetClass = (Class<?>) object;

            if (targetClass.isAnnotationPresent(NoAuthRequired.class)) {
                return; // 绕过身份验证
            }
        }

        // super.decide(authentication, object, configAttributes);
    }
}
