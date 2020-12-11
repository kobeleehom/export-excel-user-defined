package com.dongxie.mp.demo.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dongxie.export.excel.domain.ExportColumnDomain;
import com.dongxie.export.excel.util.ExportExcelUtil;
import com.dongxie.mp.demo.entity.UserEntity;
import com.dongxie.mp.demo.mapper.UserMapper;
import com.dongxie.mp.demo.service.IUserService;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  业务逻辑接口的实现类 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-12-11 <br>
 * @see com.dongxie.mp.demo.service.impl <br>
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService{

    @Autowired
    private UserMapper mapper;

    public void b2cOrderExport(HttpServletResponse response) throws IOException {
        // 查询需要导出的字段  含有是否merge列信息对象
        List<ExportColumnDomain> columns = new ArrayList<>();

        if (CollectionUtil.isEmpty(columns)) {
            log.warn("Export failed! Can not find export column config, please check!");
            return;
        }

        ExcelWriter excelWriter = ExportExcelUtil.getWriter(response.getOutputStream(), columns, "id");
        WriteSheet writeSheet = ExportExcelUtil.getSheet("导出测试");
        // 分页查询数据并写入excel
        selectPageDataWriteExcel(columns, excelWriter, writeSheet);

        ExportExcelUtil.finish(excelWriter);

    }

    /** 分页查询数据并写入excel */
    private void selectPageDataWriteExcel(List<ExportColumnDomain> columns, ExcelWriter excelWriter, WriteSheet writeSheet) {
        // 设置分页大小
//        queryDto.setPageSize(500);

//        IPage<OrderExportDto> pageData = service.queryExportList(queryDto);
        // 限制最大单次导出数量为 10000 条
//        if (Objects.nonNull(pageData) && CollectionUtil.isNotEmpty(pageData.getRecords())) {
            ExportExcelUtil.write(excelWriter, writeSheet, columns, new ArrayList<>());
            // 当且仅当 没有超过最大限制条数和当前页没到最大条数时才进去查下一页
//            while ((pageData.getCurrent() < pageData.getPages()) && (ExportParamConstants.MAX_RECORDS > pageData.offset())) {
//                queryDto.setCurrentPage(Long.valueOf(1 + pageData.getCurrent()).intValue());
//                pageData = omsOrderInfoService.queryExportList(queryDto);
//                ExportExcelUtil.write(excelWriter, writeSheet, columns, new ArrayList<>());
//                log.info("excel export write db record page no: {}", queryDto.getCurrentPage());
//            }
//        }
    }
}
