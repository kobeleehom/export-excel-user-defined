package com.dongxie.mp.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dongxie.mp.demo.dto.QueryDto;
import com.dongxie.mp.demo.entity.UserEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
