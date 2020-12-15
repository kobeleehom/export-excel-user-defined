package com.dongxie.export.excel.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongxie.export.excel.builder.ExportExcelBuilder;
import com.dongxie.export.excel.domain.ExportColumnDomain;
import com.dongxie.export.excel.strategy.AutoWidthStrategy;
import com.dongxie.export.excel.strategy.MergeStrategy;
import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.poi.ss.formula.functions.T;

import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public final class ExportExcelUtil {

    private ExportExcelUtil(){};

    /**
     * 获取 ExcelWriter 对象
     * @Author: dong.xie
     * @param outputStream 输出流
     * @param columns 列字段信息集合
     * @param uniqueColumnCode 唯一识别列标识
     * @return EasyExcel 的 ExcelWriter 对象
     */
    public static ExcelWriter getWriter(OutputStream outputStream, List<ExportColumnDomain> columns, String uniqueColumnCode, long maxRow) {
        return new ExportExcelBuilder().buildWriter(outputStream, columns, uniqueColumnCode, maxRow);
    }

    /**
     * 获取 WriteSheet 对象
     * @Author: dong.xie
     * @param name sheet名称
     * @return EasyExcel 的 WriteSheet 对象
     */
    public static WriteSheet getSheet(@NotNull String name){
        // 创建一个写sheet
        return new ExportExcelBuilder().buildSheet(name);
    }

    /**
     * 批量写入数据
     * @Author: dong.xie
     * @param excelWriter 写入器
     * @param writeSheet 写入的sheet
     * @param columns  列配置
     * @param exportList 导出数据
     */
    public static void write(@NotNull ExcelWriter excelWriter, @NotNull WriteSheet writeSheet, @NotNull List<ExportColumnDomain> columns, @NotNull List<Object> exportList){
        excelWriter.write(exportListParser(exportList, columns), writeSheet);
    }

    /**
     * 完成导出操作流
     * @Author: dong.xie
     * @param excelWriter 写入器
     */
    public static void finish(@NotNull ExcelWriter excelWriter){
        excelWriter.finish();
    }


    /**
     * 导出数据转为excelWriter可以识别的list
     * @Author: dong.xie
     * @param exportList 导出数据集合
     * @param columns 导出列配置集合
     * @return 转换后的集合数据
     */
    public static <T> List<List<String>> exportListParser(List<T> exportList, List<ExportColumnDomain> columns) {
        // 每一条记录就是一行excel 也就是一个list
        return exportList.stream().map(exportItem -> {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONStringWithDateFormat(exportItem, DatePattern.NORM_DATETIME_MS_PATTERN));
            // 遍历每一个需要写入的行 每个元素一个object 整行是一个list
            return columns.stream().map(colum -> {
                if (jsonObject.containsKey(colum.getColumnCode()) && StrUtil.isNotBlank(colum.getColumnCode())) {
                    // 看是否需要翻译字段
                    if (StrUtil.isNotBlank(colum.getValueMapping())) {
                        // 将需要翻译的对象转换为json, 直接根据code获取需要展示的值 注：如果没找到则直接返回code
                        JSONObject valueJson = JSON.parseObject(colum.getValueMapping());
                        return String.valueOf(valueJson.containsKey(jsonObject.get(colum.getColumnCode()))
                                ? valueJson.get(jsonObject.get(colum.getColumnCode()))
                                : jsonObject.get(colum.getColumnCode()));
                    }
                    return String.valueOf(jsonObject.get(colum.getColumnCode()));
                }
                return "";
            }).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }
}
