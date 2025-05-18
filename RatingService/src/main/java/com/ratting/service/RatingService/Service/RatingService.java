package com.ratting.service.RatingService.Service;

import com.ratting.service.RatingService.Data.ReqData.RatingReqData;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseStatusData;

public interface RatingService {

    ResponseBaseStatusData saveRating(RatingReqData ratingReqData);
}
