package com.hotel.service.HotelService.Controller;

import com.hotel.service.HotelService.Data.ReqData.HotelReqData;
import com.hotel.service.HotelService.Data.ResData.ResponseBaseStatusData;
import com.hotel.service.HotelService.Data.ResData.ResponseSuccessData;
import com.hotel.service.HotelService.Service.HotelService;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping(value = "/saveHotel")
    public ResponseEntity<?> saveUser(@RequestBody HotelReqData hotelReqData){
        ResponseBaseStatusData user = hotelService.saveHotel(hotelReqData);
        return new ResponseEntity<>(new ResponseSuccessData<>(user), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllHotels")
    public ResponseEntity<?> getAllHotels(
            @RequestParam(value = "per_page", required = false, defaultValue = "10") int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @Nullable @RequestParam(value = "sort_by", required = false, defaultValue = "insert_date") String sortBy,
            @Nullable @RequestParam(value = "sort_type", required = false, defaultValue = "desc") String sortType,
            @Nullable @RequestParam(value = "search", required = false) Long search) {
        return new ResponseEntity<>(hotelService.getAllHotelsData(page, size, sortBy, sortType, search), HttpStatus.OK);
    }

    @GetMapping(value = "/getHotelById")
    public ResponseEntity<?> getHotelById(
            @RequestParam(value = "hotel_id") Long hotelId) {
        return new ResponseEntity<>(hotelService.getHotelDataById(hotelId), HttpStatus.OK);
    }
}
