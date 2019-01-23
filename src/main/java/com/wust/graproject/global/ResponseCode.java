package com.wust.graproject.global;


/**
 * @ClassName ResponseCode
 * @Description TODO
 * @Author leis
 * @Date 2019/1/4 14:34
 * @Version 1.0
 **/
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEEF_LOGIN(10,"ERROR"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return msg;
    }
}
