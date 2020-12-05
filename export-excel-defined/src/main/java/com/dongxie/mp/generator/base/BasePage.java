package com.dongxie.mp.generator.base;

import lombok.Data;

/**
 * 分页实体类 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see BasePage <br>
 */
@Data
public class BasePage {

    /**
     * 当前每页显示数
     */
    private Integer size;

    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 总条数
     */
    private long total;

}
