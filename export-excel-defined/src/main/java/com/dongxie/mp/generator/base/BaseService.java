package com.dongxie.mp.generator.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 基础的service接口，所有的service都要继承这个接口  如果有什么通用的方法，可自行扩展 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see BaseService <br>
 */
public interface BaseService<T> extends IService<T> {
}
