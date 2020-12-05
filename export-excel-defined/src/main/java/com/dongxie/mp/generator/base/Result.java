package com.dongxie.mp.generator.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 前端返回json格式封装类 <br>
 *
 * @author dong.xie <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2020-02-20 <br>
 * @see Result <br>
 */
public class Result implements Serializable {

    /**
     * 返回状态码，200为正确，100为失败
     */
    private int code;

    /**
     * 返回处理信息，成功或者失败
     */
    private String msg;

    /**
     * 成功返回true，失败返回false
     */
    private Boolean success;

    /**
     * 返回给前端的数据
     */
    private Map<String, Object> extend = new HashMap<String ,Object>();

    /**
     * 成功返回的json封装体
     * @param value 原始数据
     * @return json格式
     */
    public static Result successJson(Object value){
        Result results = new Result();
        results.setCode(BaseEnum.SUCCESS.getIndex());
        results.setMsg(BaseEnum.SUCCESS.getMsg());
        results.setSuccess(true);
        results.getExtend().put("data",value);
        return results;
    }

    /**
     * 失败返回的json封装体
     * @return json格式
     */
    public static Result errorJson(){
        Result results = new Result();
        results.setCode(BaseEnum.FAIL.getIndex());
        results.setSuccess(false);
        results.setMsg(BaseEnum.FAIL.getMsg());
        return results;
    }

    /**
     * 失败返回的json封装体
     * @return json格式
     */
    public static Result errorJson(String msg){
        Result results = new Result();
        results.setCode(BaseEnum.FAIL.getIndex());
        results.setSuccess(false);
        results.setMsg(msg);
        return results;
    }

    /**
     * 失败返回的json封装体
     * @return json格式
     */
    public static Result errorJson(String msg,Integer code){
        Result results = new Result();
        results.setCode(code);
        results.setSuccess(false);
        results.setMsg(msg);
        return results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

}
