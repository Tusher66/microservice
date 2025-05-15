package com.hotel.service.hotelService.Service;

import com.hotel.service.hotelService.Data.ReqData.HotelReqData;
import com.hotel.service.hotelService.Data.ResData.PaginatedResData;
import com.hotel.service.hotelService.Data.ResData.ResponseBaseData;
import com.hotel.service.hotelService.Data.ResData.ResponseBaseStatusData;

public interface HotelService {

    ResponseBaseStatusData saveHotel(HotelReqData hotelReqData);

    PaginatedResData<?> getAllHotelsData(int page, int size, String sortBy, String sortType, Long search);

    ResponseBaseData getHotelDataById(Long hotelId);
}
