package com.klaus.demo.helloworld.anno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启数据同步，指定扫包路径
 *
 * @author Silas
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SyncScanRegister.class)
public @interface EnableDataSync {

    String[] value();

}
