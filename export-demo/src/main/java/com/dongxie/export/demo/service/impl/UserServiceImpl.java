package com.dongxie.export.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dongxie.export.excel.domain.ExportColumnDomain;
import com.dongxie.export.excel.service.IExportService;
import com.dongxie.export.demo.dto.QueryDto;
import com.dongxie.export.demo.entity.UserEntity;
import com.dongxie.export.demo.mapper.UserMapper;
import com.dongxie.export.demo.service.IUserService;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 业务逻辑接口的实现类 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-12-11 <br>
 * @see com.dongxie.mp.demo.service.impl <br>
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService, IExportService<QueryDto, UserEntity> {

    @Autowired
    private UserMapper mapper;

    @Override
    public IPage<UserEntity> queryExportPage(QueryDto query) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>().lambda().orderByAsc(UserEntity::getAge);
        if (Objects.nonNull(query.getMinAge())) {
            queryWrapper.ge(UserEntity::getAge, query.getMinAge());
        }
        if (Objects.nonNull(query.getMaxAge())) {
            queryWrapper.le(UserEntity::getAge, query.getMaxAge());
        }
        if (Objects.nonNull(query.getGender())) {
            queryWrapper.eq(UserEntity::getGender, query.getGender());
        }
        return mapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
    }

    @Override
    public void export(HttpServletResponse response, QueryDto queryDto) throws Exception {
        // 实际场景这里建议存表
        List<ExportColumnDomain> columns = ExportColumnDomain.mockColumns();

        if (CollectionUtil.isEmpty(columns)) {
            log.warn("Export failed! Can not find export column config, please check!");
            return;
        }

        this.doExport(response.getOutputStream(), queryDto, columns, "age", "用户信息");
    }

    @Override
    public Boolean init() {
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            users.add(new UserEntity().setAge(i / 20 + 10).setGender(i % 2).setName("钱赵孙" + i));
        }
        return this.saveBatch(users);
    }


}
