package com.dongxie.export.demo.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class QueryDto extends Page {

    private Integer minAge;

    private Integer maxAge;

    private Integer gender;

}
