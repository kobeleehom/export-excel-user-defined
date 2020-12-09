package com.dongxie.export.excel.builder;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dongxie.export.excel.domain.ExportColumnDomain;
import com.dongxie.export.excel.strategy.AutoWidthStrategy;
import com.dongxie.export.excel.strategy.MergeStrategy;
import com.xiaoleilu.hutool.util.StrUtil;

import javax.validation.constraints.NotNull;
import java.io.OutputStream;
import java.util.*;

public final class ExportExcelBuilder {

    /**
     * @Author: dong.xie
     * @param outputStream 输出流
     * @param columns 列字段信息集合
     * @param uniqueColumnCode 唯一识别列标识
     * @return EasyExcel 的 ExcelWriter 对象
     */
    public ExcelWriter buildWriter(OutputStream outputStream, List<ExportColumnDomain> columns, String uniqueColumnCode) {
        // 得到需要合并的 列 序号
        Set<Integer> mergeColumeIndex = new HashSet<>();
        // 找到唯一标识记录的下标(默认找订单号没有则取 0 )
        Integer uniqueIndex = 0;
        // 动态添加 表头 headList --> 所有表头行集合
        List<List<String>> headList = new ArrayList<>();
        // 构建表头等信息
        uniqueIndex = buildHeadAndParam(columns, mergeColumeIndex, uniqueIndex, headList, uniqueColumnCode);

        return EasyExcelFactory.write(outputStream)
                // 将自动关闭流设置为false 这里稍后需要分批次查询后写入的
                .autoCloseStream(Boolean.FALSE)
                // 设置表头
                .head(headList)
                // 设置合并单元格策略
                .registerWriteHandler(new MergeStrategy(10, mergeColumeIndex, uniqueIndex))
                .build();
    }

    /**
     * @Author: dong.xie
     * @param name sheet名称
     * @return EasyExcel 的 WriteSheet 对象
     */
    public WriteSheet buildSheet(@NotNull String name){
        // 创建一个写sheet
        return EasyExcelFactory
                .writerSheet(name)
                .registerWriteHandler(new AutoWidthStrategy())
                .build();
    }

    /**
     * @author: dong.xie
     * @description: 构建表头 和需要的参数
     * @param: [columns 配置列信息, mergeColumeIndex 需要合并的列, uniqueIndex 唯一识别列在excel中的列索引, headList 表头list]
     * @return: java.lang.Integer 唯一识别列在excel中的列索引
     */
    private Integer buildHeadAndParam(List<ExportColumnDomain> columns, Set<Integer> mergeColumeIndex, Integer uniqueIndex, List<List<String>> headList, String uniqueColumnCode) {
        // 获取默认指定的唯一索引列的code 只取第一条即可！！！

        for (int i = 0; i < columns.size(); i++) {
            if (Objects.nonNull(columns.get(i)) && Objects.nonNull(columns.get(i).getNeedMerge()) && columns.get(i).getNeedMerge()) {
                mergeColumeIndex.add(i);
            }
            // 找到默认唯一识别列的列索引值
            if (StrUtil.isNotBlank(uniqueColumnCode) && Objects.nonNull(columns.get(i)) && Objects.nonNull(columns.get(i).getColumnCode()) && StrUtil.equals(uniqueColumnCode, columns.get(i).getColumnCode())) {
                uniqueIndex = i;
            }
            // 追加第 i 行 的表头
            List<String> headTitle = new ArrayList<>();
            headTitle.add(columns.get(i).getColumnName());
            headList.add(headTitle);
        }
        return uniqueIndex;
    }

}
