package com.zw.model;

import lombok.Data;

public class RestResponse {
    private int errorCode; // 0 means success
    private String message;
    private Object payload;

    public static RestResponse forError() {
        RestResponse ret = new RestResponse();
        ret.errorCode = 1;
        return ret;
    }

    public static RestResponse forSuccess() {
        RestResponse ret = new RestResponse();
        ret.errorCode = 0;
        ret.message = "Success";
        return ret;
    }

    public RestResponse withMsg(String msg) {
        this.message = msg;
        return this;
    }

    public RestResponse withPayload(Object payload) {
        this.payload = payload;
        return this;
    }
}
