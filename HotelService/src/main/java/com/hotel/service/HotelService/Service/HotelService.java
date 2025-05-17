package com.hotel.service.HotelService.Service;


import com.hotel.service.HotelService.Data.ReqData.HotelReqData;
import com.hotel.service.HotelService.Data.ResData.PaginatedResData;
import com.hotel.service.HotelService.Data.ResData.ResponseBaseData;
import com.hotel.service.HotelService.Data.ResData.ResponseBaseStatusData;

public interface HotelService {

    ResponseBaseStatusData saveHotel(HotelReqData hotelReqData);

    PaginatedResData<?> getAllHotelsData(int page, int size, String sortBy, String sortType, Long search);

    ResponseBaseData getHotelDataById(Long hotelId);
}
