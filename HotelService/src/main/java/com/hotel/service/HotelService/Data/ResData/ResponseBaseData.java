package com.hotel.service.HotelService.Data.ResData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResponseBaseData<T> {
    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("details")
    private String details;

    @JsonProperty("data")
    private T data;

    public ResponseBaseData(Boolean status, Integer code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
