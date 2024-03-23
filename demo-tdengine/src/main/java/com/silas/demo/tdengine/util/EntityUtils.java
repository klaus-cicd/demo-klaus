package com.silas.demo.tdengine.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.ClassScanner;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silas.demo.tdengine.anno.TDTableEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Klaus
 */
@Slf4j
public class EntityUtils {

    private EntityUtils() {
    }


    /**
     * 获取指定包下所有被@TDTableEntity标识的类, 获取其@TableName的值
     *
     * @param packageName 包名
     * @return 表名列表
     */
    public static List<String> getTableNames(String packageName) {
        // 获取指定包名下的所有类
        Set<Class<?>> classes = ClassScanner.scanPackage(packageName);
        return classes.stream()
                .filter(clazz -> AnnotationUtil.hasAnnotation(clazz, TDTableEntity.class)
                        && AnnotationUtil.hasAnnotation(clazz, TableName.class)
                        && StringUtils.hasText(AnnotationUtil.getAnnotationValue(clazz, TableName.class, "value")))
                .map(clazz -> (String) AnnotationUtil.getAnnotationValue(clazz, TableName.class, "value"))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> tableNames = getTableNames("com.silas.demo.tdengine.entity");
        log.info("result: {}", tableNames);
    }
}
