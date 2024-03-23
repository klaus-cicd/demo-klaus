package com.silas.demo.tdengine.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识TDengine超级表对应的实体类
 *
 * @author Klaus
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TDTableEntity {

    /**
     * 是否为超级表
     *
     * @return boolean
     */
    boolean isTDSuperTable() default true;

    /**
     * 是否需要动态表名
     *
     * @return boolean
     */
    boolean needDynamicTbName() default true;

}
