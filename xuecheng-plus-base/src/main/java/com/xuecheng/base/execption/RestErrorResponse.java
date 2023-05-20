package com.xuecheng.base.execption;

import java.io.Serializable;

/**
 * @author cardo
 * @Version 1.0
 * @Description 错误信息相应模型
 * @date 2023/5/20 9:57
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}

