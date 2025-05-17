package com.hotel.service.HotelService.Data.ResData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewPaginationBaseData<T> {
    @JsonProperty("path")
    private String path;

    @JsonProperty("total")
    private Long total;

    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("current_page")
    private Integer currentPage;

    @JsonProperty("from")
    private Integer from;

    @JsonProperty("to")
    private Integer to;

    @JsonProperty("last_page")
    private Integer lastPage;

    @JsonProperty("data")
    private List<T> data;

}
