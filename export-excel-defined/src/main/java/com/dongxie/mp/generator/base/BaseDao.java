package com.dongxie.mp.generator.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * 基础的Dao接口   如果有什么通用的方法，可自行扩展 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see BaseDao <br>
 */
public interface BaseDao<Pojo> extends BaseMapper<Pojo> {
}