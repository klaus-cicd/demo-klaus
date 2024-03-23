package com.silas.demo.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Klaus
 */
public class EasyExcelUtil {


    public static void excelRespSet(HttpServletResponse response, String filename) {
        // 设置响应内容类型为Excel文件
        response.setContentType("application/vnd.ms-excel");
        // 设置响应头，指定文件名
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setCharacterEncoding("utf-8");
    }

    public static void fillExcel(HttpServletResponse response, String fileName, List<?> dataList) throws IOException {
        excelRespSet(response, fileName);

        try (ServletOutputStream outputStream = response.getOutputStream();
             InputStream inputStream = new ClassPathResource("template/SailingRecordTemplate.xlsx").getInputStream();
             ExcelWriter writer = EasyExcel.write(outputStream).withTemplate(inputStream).build()) {

            // 自定义工作表对象
            WriteSheet sheet = EasyExcel.writerSheet(0).build();
            // 填充配置: 填充列表开启自动换行, 自动换行表示每次写入一条list数据都会重新生成一行空行, 此选项默认是关闭的, 需要提前设置为true
            FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();

            // 填充标题

            // 填充数据
            writer.fill(dataList, fillConfig, sheet);
        }


    }


}
