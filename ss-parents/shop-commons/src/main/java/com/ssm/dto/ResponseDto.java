package com.ssm.dto;

import com.ssm.utils.ErrorCode;

public class ResponseDto {
    private Integer error_code;
    private String msg;
    private Object obj;

    public ResponseDto(Integer success, String s, Object obj) {
        this.error_code = error_code;
        this.msg = msg;
        this.obj = obj;
    }

    /**
     * 成功的函数
     * @return
     */
    public static ResponseDto success(Object obj){
        return new ResponseDto(ErrorCode.SUCCESS,"",obj);
    }
    public  static  ResponseDto error(Integer error_code,String msg){

        return  new ResponseDto(error_code,msg,null);
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "error_code=" + error_code +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }

    public ResponseDto() {
    }
}
