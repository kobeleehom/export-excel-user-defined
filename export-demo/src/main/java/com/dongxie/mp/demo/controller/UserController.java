package com.dongxie.mp.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.dongxie.mp.demo.service.IUserService;
import com.dongxie.mp.demo.entity.UserEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

/**
 *  接口 <br>
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see com.dongxie.mp.demo.controller <br>
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController<IUserService, UserEntity> {
    @Autowired
    private IUserService service;
}
