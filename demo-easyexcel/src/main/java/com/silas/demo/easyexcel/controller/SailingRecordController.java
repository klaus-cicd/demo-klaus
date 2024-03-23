package com.silas.demo.easyexcel.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.silas.demo.easyexcel.entity.SailingRecord;
import com.silas.demo.easyexcel.service.SailingRecordService;
import com.silas.demo.easyexcel.util.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
@RestController
public class SailingRecordController {

    private final SailingRecordService sailingRecordService;

    public SailingRecordController(SailingRecordService sailingRecordService) {
        this.sailingRecordService = sailingRecordService;
    }

    /**
     * 使用Easyexcel工具, 填充模板的形式导出
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/easy-excel/export")
    public void easyExcelExport(HttpServletResponse response) throws IOException {
        // 数据获取
        List<SailingRecord> list = sailingRecordService.list(Wrappers.<SailingRecord>lambdaQuery()
                .between(SailingRecord::getCreateTime, LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2023, 1, 1, 23, 0, 0, 0)));
        if (CollectionUtils.isEmpty(list)) {
            log.warn("No data to export!");
            return;
        }


        // 填充数据
        EasyExcelUtil.fillExcel(response, "test.xlsx", list);
    }


    @GetMapping("/hutool-excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 设置文件为下载
        // response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("fileName", "UTF-8"));

        // 设置响应内容类型为Excel文件
        response.setContentType("application/vnd.ms-excel");
        // 设置响应头，指定文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"SailingRecordTemplate.xlsx\"");


        // 数据获取
        List<SailingRecord> list = sailingRecordService.list(Wrappers.<SailingRecord>lambdaQuery().last("limit 30"));

        // 数据写出
        try (ServletOutputStream outputStream = response.getOutputStream();
             ExcelWriter writer = ExcelUtil.getWriter(true);) {
            writer.write(list, true);
            // 刷新文件流
            writer.flush(outputStream, true);
            outputStream.flush();
        }
    }

}
