package com.wwx.minishop.beans;

import java.util.HashMap;
import java.util.Map;

public class Msg {

    private int code;

    private String msg;

    private Map<String,Object> extend = new HashMap<String, Object>();

    public static Msg success(){
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("操作成功");
        return result;
    }

    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("操作失败");
        return result;
    }

    public Msg add(String key,Object value){
        this.extend.put(key,value);
        return this;
    }

    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    private void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
