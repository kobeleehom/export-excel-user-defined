package com.dongxie.mp.demo.controller;

import com.dongxie.mp.demo.entity.UserEntity;
import com.dongxie.mp.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 接口 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-12-11 <br>
 * @see com.dongxie.mp.demo.controller <br>
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService service;

    @PostMapping("")
    public ResponseEntity<Boolean> save(@Valid @RequestBody UserEntity user) {
        return ResponseEntity.ok(service.save(user));
    }

    @GetMapping("")
    public ResponseEntity<List> list() {
        return ResponseEntity.ok(service.list());
    }

}
