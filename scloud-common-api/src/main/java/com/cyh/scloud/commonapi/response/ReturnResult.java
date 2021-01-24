package com.cyh.scloud.commonapi.response;

import com.cyh.scloud.commonapi.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.boot.convert.DataSizeUnit;

import java.io.Serializable;
import java.util.Map;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  16:07
 * Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnResult<T> implements Serializable {


    private static final long serialVersionUID = -7872843447222129211L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 响应结果
     */
    private T data;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 错误信息
     */
    private Map<String, Object> error;

    public ReturnResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }


    public ReturnResult okResult(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public ReturnResult errorResult() {
        this.code = ResultCode.ERROR.getCode();
        this.message = ResultCode.ERROR.getMessage();
        return this;
    }
}
