package com.dongxie.export.excel.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @version: V1.0
 * @author: dong.xie
 * @description: 导出字段域
 * @data: 2020/12/4 15:54
 **/
@Data
@Accessors(chain = true)
public class ExportColumnDomain {

    /**
     * 配置id
     */
    private Integer id;

    /**
     * 导出的服务类型 便于服务于同一系统中的不同场景
     */
    private Integer serviceType;

    /**
     * 字段编码 也即为 对应行数据在持久化中的字段名
     */
    private String columnCode;

    /**
     * 字段名称  也即为 表头显示信息
     */
    private String columnName;

    /**
     * 配置状态 -- 0用户未选择该字段， 1用户选择了该字段
     */
    private Integer configStatus;

    /**
     * 是否保留字段即为用户必选导出
     */
    private Boolean require;

    /**
     * 在excel中是否需要合并单元格，比如订单中的商品明细需要将这个置为false
     */
    private Boolean needMerge;

    /**
     * 字段翻译映射对象，不需要翻译的为空即可，需要翻译的按键值对的形势传入json",
     */
    private String valueMapping;
}
