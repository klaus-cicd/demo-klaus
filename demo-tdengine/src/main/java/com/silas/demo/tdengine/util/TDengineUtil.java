package com.silas.demo.tdengine.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silas.demo.tdengine.anno.TDTagField;
import com.silas.demo.tdengine.constant.SqlConstant;
import com.silas.demo.tdengine.strategy.AbstractDynamicTbNameStrategy;
import com.silas.demo.tdengine.strategy.DefaultDynamicTbNameStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Klaus
 */
@Slf4j
@Configuration
public class TDengineUtil {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TDengineUtil(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void insertUsing(Object object) {
        insertUsing(object, new DefaultDynamicTbNameStrategy());
    }

    public <T> void batchInsertUsing(Class<T> clazz, List<T> entityList, AbstractDynamicTbNameStrategy dynamicTbNameStrategy) {
        batchInsertUsing(clazz, entityList, 1000, dynamicTbNameStrategy);
    }

    public void insertUsing(Object object, AbstractDynamicTbNameStrategy dynamicTbNameStrategy) {
        // 获取超级表表名&所有Field
        Pair<String, List<Field>> tbNameAndFieldsPair = getTbNameAndFieldListPair(object.getClass());
        // 获取SQL&参数值
        Pair<String, Map<String, Object>> finalSqlAndParamsMapPair = getFinalSql(object, tbNameAndFieldsPair.getValue(), tbNameAndFieldsPair.getKey(), dynamicTbNameStrategy);

        int result = namedParameterJdbcTemplate.update(finalSqlAndParamsMapPair.getKey(), finalSqlAndParamsMapPair.getValue());
        log.debug("Create and insert, sql: {}, result:{}", finalSqlAndParamsMapPair.getKey(), result);
    }

    private Pair<String, List<Field>> getTbNameAndFieldListPair(Class<?> clazz) {
        // 获取超级表名称
        Assert.isTrue(AnnotationUtil.hasAnnotation(clazz, TableName.class), "createAndInsert fail, no @TableName found!");

        // 获取所有字段
        List<Field> fieldList = getAllFields(clazz);
        Assert.notEmpty(fieldList, "createAndInsert fail, no field found!");
        String sTbName = AnnotationUtil.getAnnotationValue(clazz, TableName.class, "value");

        return Pair.of(sTbName, fieldList);
    }


    public <T> void batchInsertUsing(Class<T> clazz, List<T> entityList, int pageSize, AbstractDynamicTbNameStrategy dynamicTbNameStrategy) {
        // 获取超级表表名&所有字段
        Pair<String, List<Field>> tbNameAndFieldsPair = getTbNameAndFieldListPair(clazz);

        // 目前仅支持同子表的数据批量插入, 所以随意取一个对象的tag的值都是一样的
        List<List<T>> partition = ListUtil.partition(entityList, pageSize);
        T t = partition.get(0).get(0);

        List<Field> tagFields = getAllFields(t.getClass()).stream()
                .filter(field -> field.isAnnotationPresent(TDTagField.class))
                .collect(Collectors.toList());

        Map<String, Object> tagValueMap = getTagValueMap(tagFields, t);

        for (List<T> list : partition) {
            Map<String, Object> paramsMap = new HashMap<>(list.size());
            paramsMap.putAll(tagValueMap);
            StringBuilder finalSql = new StringBuilder(justGetSql(t, tbNameAndFieldsPair.getKey(), tbNameAndFieldsPair.getValue(), dynamicTbNameStrategy, paramsMap));
            for (int i = 0; i < list.size(); i++) {
                T entity = list.get(i);
                List<Field> fields = getAllFields(entity.getClass());
                finalSql.append(jointSqlValue(entity, fields, paramsMap, i));
            }
            int result = namedParameterJdbcTemplate.update(finalSql.toString(), paramsMap);
            log.debug("{} =====execute result====>{}", finalSql, result);
        }
    }

    private <T> Map<String, Object> getTagValueMap(List<Field> tagFields, T t) {
        Map<String, Object> tagValueMap = new HashMap<>(tagFields.size());
        for (Field tagField : tagFields) {
            tagValueMap.put(tagField.getName(), ReflectUtil.getFieldValue(t, tagField));
        }
        return tagValueMap;
    }

    private <T> String jointSqlValue(T entity, List<Field> fields, Map<String, Object> paramsMapList, int index) {
        Map<Boolean, List<Field>> fieldGroups = fields.stream()
                .collect(Collectors.partitioningBy(field -> field.isAnnotationPresent(TDTagField.class)));
        List<Field> commFields = fieldGroups.get(Boolean.FALSE);

        return commFields.stream()
                .map(field -> {
                    String fieldName = field.getName();
                    paramsMapList.put(fieldName + index, ReflectUtil.getFieldValue(entity, field));
                    return ":" + fieldName + index;
                })
                .collect(getCharSequenceStringCollector());
    }

    private String justGetSql(Object object, String sTbName, List<Field> fieldList, AbstractDynamicTbNameStrategy dynamicTbNameStrategy, Map<String, Object> map) {
        // 根据是否为TAG字段做分组
        Pair<List<Field>, List<Field>> fieldsPair = getFieldsPair(fieldList);
        // 获取TAGS字段名称&对应的值
        String tagFieldSql = getFieldNameAndValues(object, fieldsPair.getKey(), map, true);
        // 获取普通字段的名称
        String commFieldSql = getCommFieldNames(fieldsPair.getValue());
        // 根据策略生成表名
        String childTbName = dynamicTbNameStrategy.dynamicTableName(sTbName);
        return SqlConstant.INSERT_INTO + childTbName + SqlConstant.USING + sTbName + tagFieldSql + commFieldSql;
    }


    private Pair<String, Map<String, Object>> getFinalSql(Object object, List<Field> fieldList, String sTbName, AbstractDynamicTbNameStrategy dynamicTbNameStrategy) {
        Map<String, Object> paramsMap = new HashMap<>(fieldList.size());

        // 根据是否为TAG字段做分组
        Pair<List<Field>, List<Field>> fieldsPair = getFieldsPair(fieldList);

        // 获取TAGS字段相关SQL
        String tagFieldSql = getFieldNameAndValues(object, fieldsPair.getKey(), paramsMap, true);
        // 获取普通字段相关SQL
        String commFieldSql = getFieldNameAndValues(object, fieldsPair.getValue(), paramsMap, false);

        // 根据策略生成表名
        String childTbName = dynamicTbNameStrategy.dynamicTableName(sTbName);

        // 拼接最终SQL
        String finalSql = SqlConstant.INSERT_INTO + childTbName + SqlConstant.USING + sTbName + tagFieldSql + commFieldSql;

        return Pair.of(finalSql, paramsMap);
    }

    private Pair<List<Field>, List<Field>> getFieldsPair(List<Field> fieldList) {
        Map<Boolean, List<Field>> fieldGroups = fieldList.stream().collect(Collectors.partitioningBy(field -> field.isAnnotationPresent(TDTagField.class)));
        List<Field> tagFields = fieldGroups.get(Boolean.TRUE);
        List<Field> commFields = fieldGroups.get(Boolean.FALSE);
        return Pair.of(tagFields, commFields);
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        List<Field> fieldList = new ArrayList<>();
        if (null != superclass) {
            Field[] superField = ClassUtil.getDeclaredFields(superclass);
            if (!ArrayUtil.isEmpty(superField)) {
                fieldList.addAll(Arrays.asList(superField));
            }
        }
        Field[] fields = ClassUtil.getDeclaredFields(clazz);
        if (ArrayUtil.isEmpty(fields)) {
            return Collections.emptyList();
        }
        fieldList.addAll(Arrays.asList(fields));
        return fieldList;
    }

    private String getFieldNameAndValues(Object object, List<Field> fields, Map<String, Object> paramsMap, boolean isTag) {
        if (CollectionUtils.isEmpty(fields)) {
            return StrUtil.EMPTY;
        }

        List<String> fieldValueParamNames = new ArrayList<>();
        String fieldNameStr = fields.stream().map(field -> {
            String fieldName = field.getName();
            fieldValueParamNames.add(field.getName());
            paramsMap.put(fieldName, ReflectUtil.getFieldValue(object, field));
            return StrUtil.toUnderlineCase(fieldName);
        }).collect(getCharSequenceStringCollector());

        String fieldValueParamsStr = fieldValueParamNames.stream().map(item -> ":" + item).collect(getCharSequenceStringCollector());
        return fieldNameStr + (isTag ? SqlConstant.TAGS : SqlConstant.VALUES) + fieldValueParamsStr;
    }

    private String getCommFieldNames(List<Field> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return StrUtil.EMPTY;
        }

        String finalSql = fields.stream().map(field -> {
            String fieldName = field.getName();
            return StrUtil.toUnderlineCase(fieldName);
        }).collect(getCharSequenceStringCollector());

        return finalSql + SqlConstant.VALUES;
    }

    private Collector<CharSequence, ?, String> getCharSequenceStringCollector() {
        return Collectors.joining(SqlConstant.COMMA, SqlConstant.LEFT_BRACKET, SqlConstant.RIGHT_BRACKET);
    }
}
