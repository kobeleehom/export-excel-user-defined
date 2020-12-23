package com.dongxie.export.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongxie.export.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-12-11 <br>
 * @see com.dongxie.mp.demo.mapper <br>
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
