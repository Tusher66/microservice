package com.hotel.service.HotelService.ServiceImpl;

import com.hotel.service.HotelService.Data.ReqData.HotelReqData;
import com.hotel.service.HotelService.Data.ResData.*;
import com.hotel.service.HotelService.Exception.CrudException;
import com.hotel.service.HotelService.Model.Hotel;
import com.hotel.service.HotelService.Repository.HotelRepository;
import com.hotel.service.HotelService.Service.HotelService;
import com.hotel.service.HotelService.Utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public ResponseBaseStatusData saveHotel(HotelReqData hotelReqData) {

        try{
            Hotel hotel = Hotel.builder()
                    .name(hotelReqData.getName())
                    .about(hotelReqData.getAbout())
                    .insertDate(LocalDateTime.now())
                    .location(hotelReqData.getLocation())
                    .build();
            hotelRepository.save(hotel);

            return ResponseBaseStatusData.builder()
                    .status(true)
                    .code(1)
                    .message("SUCCESSFULLY SAVED.")
                    .build();
        } catch (Exception e){
            throw new CrudException("AN UNEXPECTED ERROR OCCURRED.", e.getMessage());
        }

    }

    @Override
    public PaginatedResData getAllHotelsData(int page, int size, String sortBy, String sortType, Long search) {

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "insert_date";
            sortType = "desc";
        }

        if (sortType == null || sortType.isEmpty()) {
            sortType = "desc";
        }

        Pageable pageRequest = PageUtils.generatePageRequest((page - 1), size, sortBy, sortType);
        Page<Hotel> pageResult = null;

        pageResult = hotelRepository.findAllHotel(search, pageRequest);

        List<HotelResData> result = pageResult.stream().map(this::hotelListResDataMap).toList();

        RequestBaseData request = RequestBaseData.builder()
                .perPage(size)
                .page(page)
                .sortBy(sortBy)
                .sortType(sortType)
                .build();

        return PageUtils.getPaginatedResData(page, size, request, pageRequest,
                pageResult.getTotalElements(), pageResult.getTotalPages(), result);
    }
    private HotelResData hotelListResDataMap(Hotel hotel) {

        return HotelResData.builder()
                .hotelId(hotel.getHotelId())
                .name(hotel.getName())
                .about(hotel.getAbout())
                .location(hotel.getLocation())
                .build();
    }

    @Override
    public ResponseBaseData getHotelDataById(Long hotelId) {
        Optional<Hotel> UserData = hotelRepository.findById(hotelId);
        return ResponseBaseData.builder()
                .status(true)
                .code(1)
                .message("Data Found")
                .data(UserData)
                .build();
    }

}
