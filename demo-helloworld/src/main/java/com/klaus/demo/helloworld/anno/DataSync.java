package com.klaus.demo.helloworld.anno;

import java.lang.annotation.*;

/**
 * 标记是否需要同步
 *
 * @author Silas
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSync {

    boolean enabled() default true;
    /**
     * 推送式Cron表达式
     *
     * @return {@link String }
     */
    String pushCron() default "";
    /**
     * 拉取式Cron表达式
     *
     * @return {@link String }
     */
    String pullCron() default "";
    /**
     * 指定接口前缀（为空则不创建接口）
     *
     * @return {@link String }
     */
    String uriPrefix() default "";

}
