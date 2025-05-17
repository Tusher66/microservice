package com.hotel.service.HotelService.Data.ReqData;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HotelReqData {

    private Long hotelId;
    private String name;
    private String about;
    private String location;
}
