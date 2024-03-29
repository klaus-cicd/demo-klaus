package com.klaus.demo.mp.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Klaus
 */
@Data
@NoArgsConstructor
public class PageVO<T> {

    /**
     * 数据量总数
     */
    private Long total;

    /**
     * 当前PageSize
     */
    private Long size;

    /**
     * 当前页码
     */
    private Long current;

    /**
     * 是否还有下一页
     */
    private Boolean hasNextPage;

    /**
     * 当前页数据列表
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> dataList;

    public static <T> PageVO<T> fromPage(Page<T> page) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setSize(page.getSize());
        pageVO.setTotal(page.getTotal());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setDataList(page.getRecords());
        pageVO.setHasNextPage(page.hasNext());
        return pageVO;
    }
}
