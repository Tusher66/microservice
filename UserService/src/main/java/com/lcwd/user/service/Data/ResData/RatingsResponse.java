package com.lcwd.user.service.Data.ResData;

import lombok.Data;

import java.util.List;

@Data
public class RatingsResponse {
    private boolean status;
    private int code;
    private String message;
    private Object details; // or your Details DTO if applicable
    private List<Rating> data;
}
