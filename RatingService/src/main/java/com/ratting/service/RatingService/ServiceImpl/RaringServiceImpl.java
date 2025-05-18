package com.ratting.service.RatingService.ServiceImpl;

import com.ratting.service.RatingService.Data.ReqData.RatingReqData;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseStatusData;
import com.ratting.service.RatingService.Exception.CrudException;
import com.ratting.service.RatingService.Model.Rating;
import com.ratting.service.RatingService.Repository.RatingRepository;
import com.ratting.service.RatingService.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RaringServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public ResponseBaseStatusData saveRating(RatingReqData ratingReqData) {
        try{
            Rating rating = Rating.builder()
                    .hotelId(ratingReqData.getHotelId())
                    .userId(ratingReqData.getUserId())
                    .rating(ratingReqData.getRating())
                    .feedback(ratingReqData.getFeedback())
                    .insertDate(LocalDateTime.now())
                    .build();
            ratingRepository.save(rating);

            return ResponseBaseStatusData.builder()
                    .status(true)
                    .code(1)
                    .message("SUCCESSFULLY SAVED.")
                    .build();
        } catch (Exception e){
            throw new CrudException("AN UNEXPECTED ERROR OCCURRED.", e.getMessage());
        }

    }
}
