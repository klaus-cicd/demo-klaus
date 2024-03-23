package com.silas.demo.helloworld;

import com.klaus.fd.utils.JsonUtil;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootTest
public class StreamSortTest {

    private List<StreamSortEntity> list;
    private Comparator<StreamSortEntity> streamSortEntityComparator;

    @BeforeEach
    void getStreamSortEntities() {
        List<StreamSortEntity> list = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 10; i++) {
            StreamSortEntity streamSortEntity = new StreamSortEntity();
            streamSortEntity.setStr("" + i);
            streamSortEntity.setIntA(600 - i);
            streamSortEntity.setADouble((double) i);
            streamSortEntity.setABoolean(i % 2 == 0);
            streamSortEntity.setLongA(streamSortEntity.getABoolean() ? 2L * i : 1000L - i);
            streamSortEntity.setLocalDateTime(i % 2 == 0 ? now.plusDays(i) : now.minusHours(i));
            streamSortEntity.setSortEnum(i % 2 == 0 ? SortEnum.A : SortEnum.B);
            list.add(streamSortEntity);
        }

        this.list = list;
    }

    @AfterEach
    void log() {
        log.info("list: {}", JsonUtil.toJsonStr(list.stream()
                .sorted(streamSortEntityComparator)
                .collect(Collectors.toList()))
        );
    }

    @Test
    void testSortA() {
        this.streamSortEntityComparator = Comparator
                .comparing(StreamSortEntity::getABoolean)
                .thenComparing(StreamSortEntity::getIntA)
                .thenComparing(StreamSortEntity::getLongA)
                // 未指定类型的字符串 -> 降序
                .thenComparing(StreamSortEntity::getStr)
                // 默认升序
                .thenComparing(StreamSortEntity::getLocalDateTime);
    }

    @Test
    void testSortB() {
        this.streamSortEntityComparator = Comparator
                .<StreamSortEntity, Integer>comparing(item -> item.getSortEnum().value, Comparator.reverseOrder())
                .thenComparing(item -> item.aBoolean)
                .thenComparingLong(StreamSortEntity::getLongA).reversed();
    }

    @Test
    void testSortC() {
        this.streamSortEntityComparator = Comparator
                .comparingInt(StreamSortEntity::getIntA)
                .thenComparingLong(StreamSortEntity::getLongA)
                .thenComparingDouble(StreamSortEntity::getADouble);
    }

    @Test
    void testSortDouble() {
        this.streamSortEntityComparator = Comparator
                // 未明确类型 -> 升序
                .comparing(StreamSortEntity::getADouble);
    }

    @Test
    void testSortDoubleB() {
        this.streamSortEntityComparator = Comparator
                // 升序
                .comparing(StreamSortEntity::getIntA)
                // 明确类型 -> 降序
                .thenComparingDouble(StreamSortEntity::getADouble);
    }

    @Test
    void testSortCustom() {
        this.streamSortEntityComparator = new Comparator<StreamSortEntity>() {
            @Override
            public int compare(StreamSortEntity o1, StreamSortEntity o2) {
                // 自定义排序 最终排序时会执行该方法
                // 遍历列表, 每次元素都是o1, 拿下一个元素作为o2
                // 返回值为int, 默认就是认为你是拿o1-o2, 然后根据结果大小值进行排序(小的排前), 当大于0时, 说明o1大, 则会将o1和o2进行换位, 所以在默认情况下, On > On-1, 也就是越往后越大, 也就是升序;
                // 如果我们强制写成 o2-o1 (即使你写的是o2-o1,它也把你当成是o1-o2), 同样的两个数, 比对结果就会取反;
                // 举例: o1=1, o2=2, 原1-2=-1<0, 所以认为入参o1<入参o2, 位置不变, 但是取反后变成2-1=1>0, 那就会认为入参o1>入参o2, 要将o1和o2位置进行调换, 那不就是变成2,1 也就是降序
                return o2.intA - o1.intA;
            }
        }.thenComparing(StreamSortEntity::getABoolean);
    }


    @Test
    void testSortCustomA() {
        // 简写
        this.streamSortEntityComparator = ((Comparator<StreamSortEntity>) (o1, o2) -> o2.intA - o1.intA)
                .thenComparing(StreamSortEntity::getABoolean);
    }


    @Test
    void testSortCustomB() {
        this.streamSortEntityComparator = Comparator
                .comparing(StreamSortEntity::getABoolean)
                // 默认是o1 - o2 升序, o2 - o1 则是降序, 等价于直接thenComparingInt(StreamSortEntity::getIntA)
                .thenComparing((o1, o2) -> o2.intA - o1.intA);

    }

    @Data
    static class StreamSortEntity {

        private Long longA;

        private Integer intA;

        private Boolean aBoolean;

        private String str;

        private Double aDouble;

        private LocalDateTime localDateTime;

        private SortEnum sortEnum;
    }

    @Getter
    enum SortEnum {
        A(1),
        B(2);
        private final Integer value;

        SortEnum(Integer value) {
            this.value = value;
        }
    }
}
