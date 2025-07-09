package com.lcwd.user.service.Data.ResData;

import lombok.Data;

@Data
public class CompanyResponse {
    private boolean status;
    private int code;
    private String message;
    private Object details;
    private Company data;
}
