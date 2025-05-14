package com.lcwd.user.service.Data.ResData;

public class ResponseSuccessData<T> extends ResponseBaseData<T> {

    public ResponseSuccessData(T data) {
        super(true, 1, "DATA FOUND.", data);
    }

    public ResponseSuccessData(String message, T data) {
        super(true, 1, message, data);
    }

}
