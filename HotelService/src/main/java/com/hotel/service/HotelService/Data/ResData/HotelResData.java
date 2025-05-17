package com.hotel.service.HotelService.Data.ResData;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HotelResData {

    private Long hotelId;
    private String name;
    private String about;
    private String location;
}
