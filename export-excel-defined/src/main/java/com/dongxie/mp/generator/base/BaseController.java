package com.dongxie.mp.generator.base;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * Description:基础Controller <br>
 *
 * 所有的Controller继承这个类,如果有什么通用的方法，可自行扩展
 * getList addData addDataBatch updateById updateByColumn
 * deleteByColumn deleteById deleteByIds
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate ${date} <br>
 * @see com.dongxie.mp.generator.base.BaseController <br>
 */
@SuppressWarnings("unchecked")
public class BaseController<Service extends IService, T> {

    private static final Logger log = LoggerFactory.getLogger(BaseMapper.class);

    private Service service;

    @Autowired
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * 获取所有的数据
     * 
     * @return 返回前端json数据
     */
    @GetMapping("getList")
    public Result getList() {
        List<T> list = service.list();
        return Result.successJson(list);
    }

    /**
     * 分页查询
     * 
     * @param pn 分页的实体类
     * @return 返回前端json数据
     */
    @GetMapping("getPageList")
    public Result getPageList(@RequestBody BasePage pn) {
        Page<T> pojo = new Page<>(pn.getCurrent(), pn.getSize());
        IPage<T> page = service.page(pojo);
        log.info("总条数 ------> " + page.getTotal());
        log.info("当前页数 ------> " + page.getCurrent());
        log.info("当前每页显示数 ------> " + page.getSize());
        log.info("数据 ------> " + page.getRecords());
        return Result.successJson(page);
    }

    /**
     * 增加一条数据
     * 
     * @param pojo 任意一个类型的实体类
     * @return 返回前端json数据
     */
    @PostMapping("addData")
    public Result addData(@RequestBody T pojo) {
        boolean flag = service.save(pojo);
        if (flag) {
            return Result.successJson("成功添加一条数据！！");
        }
        return Result.errorJson();
    }

    /**
     * 添加一组数据
     * 
     * @param pojo 实体类的集合
     * @return 返回前端json数据
     */
    @PostMapping("addDataBatch")
    public Result addData(@RequestBody Collection<T> pojo) {
        boolean flag = service.saveBatch(pojo, 500);
        if (flag) {
            return Result.successJson("成功添加一组数据！！");
        }
        return Result.errorJson();
    }

    /**
     * 根据id修改一条数，数据中必须有主键id字段
     * 
     * @param pojo 任意实体类
     * @return 返回前端json数据
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody T pojo) {
        boolean flag = service.updateById(pojo);
        if (flag) {
            return Result.successJson("成功修改一条数据");
        }
        return Result.errorJson();
    }

    /**
     * 根据其他属性字段修改一条数据
     * 
     * @param pojo 任意类型实体类
     * @param column 具体字段
     * @param value 具体字段的值
     * @return 返回前端json数据
     */
    @PostMapping("updateByColumn")
    public Result update(@RequestBody T pojo, @RequestParam("column") String column,
        @RequestParam(name = "value") String value) {
        Wrapper<T> wrapper = Wrappers.<T> update().eq(column, value);
        boolean flag = service.update(pojo, wrapper);
        if (flag) {
            return Result.successJson("成功修改一条数据");
        }
        return Result.errorJson();
    }

    /**
     * 要根据哪个字段删除一条数据
     * 
     * @param column 要根据哪个字段删除
     * @param value 字段的值
     * @return 返回前端json数据
     */
    @DeleteMapping("deleteByColumn")
    public Result delete(@RequestParam("column") String column, @RequestParam("value") String value) {
        Wrapper<T> ew = Wrappers.<T> query().eq(column, value);
        boolean flag = service.remove(ew);
        if (flag) {
            return Result.successJson("成功删除一条数据");
        }
        return Result.errorJson();
    }

    /**
     * 根据id删除一条数据
     * 
     * @param id 传入的id值
     * @return 返回前端json数据
     */
    @DeleteMapping("deleteById")
    public Result delete(@RequestParam("id") Integer id) {
        boolean flag = service.removeById(id);
        if (flag) {
            return Result.successJson("成功删除一条数据");
        }
        return Result.errorJson();
    }

    /**
     * 根据一组id删除数据
     * 
     * @param ids 一组id
     * @return 返回前端json数据
     */
    @DeleteMapping("deleteByIds")
    public Result delete(@RequestParam(value = "ids[]") List<Integer> ids) {
        boolean flag = service.removeByIds(ids);
        if (flag) {
            return Result.successJson("成功删除一组数据");
        }
        return Result.errorJson();
    }

}