package com.wust.graproject.global;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName ResultDataDto
 * @Description TODO
 * @Author leis
 * @Date 2019/1/4 14:30
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultDataDto<T> implements Serializable {

    private static final long serialVersionUID = 2183290531908708059L;

    private int status;
    private String msg;
    private T data;

    public ResultDataDto(int status) {
        this.status = status;
    }

    public ResultDataDto(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResultDataDto(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T> ResultDataDto<T> operationSuccess() {
        return new ResultDataDto<>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ResultDataDto<T> operationSuccessByMessage(String msg) {
        return new ResultDataDto<>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ResultDataDto<T> operationSuccess(T data) {
        return new ResultDataDto<>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ResultDataDto<T> operationSuccess(String msg, T data) {
        return new ResultDataDto<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResultDataDto<T> operationError() {
        return new ResultDataDto<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ResultDataDto<T> operationErrorByMessage(String errorMessage) {
        return new ResultDataDto<>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ResultDataDto<T> operationErrorByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ResultDataDto<>(errorCode, errorMessage);
    }
}
