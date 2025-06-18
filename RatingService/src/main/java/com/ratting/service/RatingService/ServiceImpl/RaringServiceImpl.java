package com.ratting.service.RatingService.ServiceImpl;

import com.ratting.service.RatingService.Data.ReqData.RatingReqData;
import com.ratting.service.RatingService.Data.ResData.PaginatedResData;
import com.ratting.service.RatingService.Data.ResData.RatingResData;
import com.ratting.service.RatingService.Data.ResData.RequestBaseData;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseStatusData;
import com.ratting.service.RatingService.Exception.CrudException;
import com.ratting.service.RatingService.Model.Rating;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseData;
import com.ratting.service.RatingService.Repository.RatingRepository;
import com.ratting.service.RatingService.Service.RatingService;
import com.ratting.service.RatingService.Utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public PaginatedResData getAllRatingData(int page, int size, String sortBy, String sortType, Long search) {

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "insert_date";
            sortType = "desc";
        }

        if (sortType == null || sortType.isEmpty()) {
            sortType = "desc";
        }

        Pageable pageRequest = PageUtils.generatePageRequest((page - 1), size, sortBy, sortType);
        Page<Rating> pageResult = null;

        pageResult = ratingRepository.findAllRating(search, pageRequest);

        List<RatingResData> result = pageResult.stream().map(this::ratingListResDataMap).toList();

        RequestBaseData request = RequestBaseData.builder()
                .perPage(size)
                .page(page)
                .sortBy(sortBy)
                .sortType(sortType)
                .build();

        return PageUtils.getPaginatedResData(page, size, request, pageRequest,
                pageResult.getTotalElements(), pageResult.getTotalPages(), result);
    }
    private RatingResData ratingListResDataMap(Rating rating) {

        return RatingResData.builder()
                .ratingId(rating.getRatingId())
                .userId(rating.getUserId())
                .hotelId(rating.getHotelId())
                .feedback(rating.getFeedback())
                .rating(rating.getRating())
                .insertDate(rating.getInsertDate())
                .build();
    }

    @Override
    public ResponseBaseData getHotelById(Long hotelId) {
        List<Rating> RatingData = ratingRepository.findByHotelId(hotelId);
        return ResponseBaseData.builder()
                .status(true)
                .code(1)
                .message("Data Found")
                .data(RatingData)
                .build();
    }

    @Override
    public ResponseBaseData getUserById(Long userId) {
        List<Rating> RatingData = ratingRepository.findByUserId(userId);
        return ResponseBaseData.builder()
                .status(true)
                .code(1)
                .message("Data Found")
                .data(RatingData)
                .build();
    }



}
