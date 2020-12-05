package com.dongxie.mp.demo.mapper;

import com.dongxie.mp.demo.entity.UserEntity;
import com.dongxie.mp.generator.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see com.dongxie.mp.demo.mapper <br>
 */
@Mapper
public interface UserMapper extends BaseDao<UserEntity> {

}
