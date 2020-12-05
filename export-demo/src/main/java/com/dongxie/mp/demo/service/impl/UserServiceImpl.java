package com.dongxie.mp.demo.service.impl;

import com.dongxie.mp.demo.entity.UserEntity;
import com.dongxie.mp.demo.mapper.UserMapper;
import com.dongxie.mp.demo.service.IUserService;
import com.dongxie.mp.generator.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 *  业务逻辑接口的实现类 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see com.dongxie.mp.demo.service.impl <br>
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Autowired
    private UserMapper mapper;

}
