package com.ratting.service.RatingService.Service;

import com.ratting.service.RatingService.Data.ReqData.RatingReqData;
import com.ratting.service.RatingService.Data.ResData.PaginatedResData;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseData;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseStatusData;

public interface RatingService {

    ResponseBaseStatusData saveRating(RatingReqData ratingReqData);

    PaginatedResData<?> getAllRatingData(int page, int size, String sortBy, String sortType, Long search);
    /*ResponseBaseData getHotelDataById(Long hotelId);*/
}
