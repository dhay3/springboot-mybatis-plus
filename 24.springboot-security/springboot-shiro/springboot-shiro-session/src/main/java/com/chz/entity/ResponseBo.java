package com.chz.entity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBo extends HashMap<String, Object> {
    private static final long serialVersionUID = 8621322679582023703L;

    /**
     * 初始化,new往该对象中放入两个键值
     */
    public ResponseBo() {
        this.put("code", 0);
        this.put("msg", "操作成功");
    }
    public static ResponseBo error(){
        return error(1,"操作失败");
    }
    public static ResponseBo error(String msg) {
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("code", 500);
        responseBo.put("msg", msg);
        return responseBo;
    }

    public static ResponseBo error(int code, String msg) {
        ResponseBo responseBo = new ResponseBo();
        //map会覆盖原有的值
        responseBo.put("code", code);
        responseBo.put("msg", msg);
        return responseBo;
    }

    public static ResponseBo ok() {
        return new ResponseBo();
    }

    /**
     *
     * @param msg 自定义的成功信息
     */
    public static ResponseBo ok(String msg){
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("msg",msg);
        return responseBo;
    }

    /**
     * @param map 设置自定义的属性
     */
    public static ResponseBo ok(Map<String,Object> map){
        ResponseBo responseBo = new ResponseBo();
        responseBo.putAll(map);
        return responseBo;
    }
}
