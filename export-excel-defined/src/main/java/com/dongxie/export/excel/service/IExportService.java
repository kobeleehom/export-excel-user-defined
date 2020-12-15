package com.dongxie.export.excel.service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongxie.export.excel.constants.ExportConstants;
import com.dongxie.export.excel.domain.ExportColumnDomain;
import com.dongxie.export.excel.util.ExportExcelUtil;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;

import java.util.List;
import java.util.Objects;

/**
 * 导出获取数据并写入数据库的接口 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 */
public interface IExportService<Q extends Page, R> {

    default void selectPageDataWriteExcel(Q query, IPage<R> pageData, List<ExportColumnDomain> columns, ExcelWriter excelWriter, WriteSheet writeSheet) {
        // 限制最大单次导出数量为 10000 条
        if (Objects.nonNull(pageData) && CollectionUtil.isNotEmpty(pageData.getRecords())) {
            // 当且仅当 没有超过最大限制条数和当前页没到最大条数时才进去查下一页
            while ((pageData.getCurrent() <= pageData.getPages()) && (ExportConstants.MAX_RECORDS > pageData.offset())) {
                List<List<String>> lists = ExportExcelUtil.exportListParser(pageData.getRecords(), columns);
                excelWriter.write(lists, writeSheet);
                query.setCurrent(1 + pageData.getCurrent());
                pageData = this.queryExportPage(query);
            }
        }
    }

    /**
     * 导出分页查询结果校验
     */
    default void assertExportData(long total) throws Exception {
        // 导出空的没意义
        if (total < 1) {
            throw new Exception("导出数据为空！");
        }
        // 指定最大导出的行数 抛出业务异常
        if (Long.valueOf(total).intValue() > ExportConstants.MAX_RECORDS) {
            throw new Exception(StrUtil.format("数据导出超过{}行,请筛选后再导出.", ExportConstants.MAX_RECORDS));
        }
    }

    /**
     * 分页查询导出数据
     *
     * @param query 分页查询条件
     * @return 分页数据对象
     * @Author: dong.xie
     */
    IPage<R> queryExportPage(Q query);
}
