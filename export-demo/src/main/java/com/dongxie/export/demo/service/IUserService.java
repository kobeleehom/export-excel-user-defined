package com.dongxie.export.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dongxie.export.demo.dto.QueryDto;
import com.dongxie.export.demo.entity.UserEntity;

import javax.servlet.http.HttpServletResponse;

/**
 *  业务逻辑的接口类 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-12-11 <br>
 * @see com.dongxie.mp.demo.service <br>
 */
public interface IUserService extends IService<UserEntity> {

    void export(HttpServletResponse response, QueryDto queryDto) throws Exception;

    Boolean init();
}
