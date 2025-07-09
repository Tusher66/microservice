package com.ratting.service.RatingService.Controller;


import com.ratting.service.RatingService.Data.ReqData.RatingReqData;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseStatusData;
import com.ratting.service.RatingService.Data.ResData.ResponseSuccessData;
import com.ratting.service.RatingService.Service.RatingService;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping(value = "/saveRating")
    public ResponseEntity<?> saveUser(@RequestBody RatingReqData ratingReqData){
        ResponseBaseStatusData user = ratingService.saveRating(ratingReqData);
        return new ResponseEntity<>(new ResponseSuccessData<>(user), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllRating")
    public ResponseEntity<?> getAllRating(
            @RequestParam(value = "per_page", required = false, defaultValue = "10") int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @Nullable @RequestParam(value = "sort_by", required = false, defaultValue = "insert_date") String sortBy,
            @Nullable @RequestParam(value = "sort_type", required = false, defaultValue = "desc") String sortType,
            @Nullable @RequestParam(value = "search", required = false) Long search) {
        return new ResponseEntity<>(ratingService.getAllRatingData(page, size, sortBy, sortType, search), HttpStatus.OK);
    }

    @GetMapping(value = "/getRatingByCompanyId")
    public ResponseEntity<?> getCompanyById(
            @RequestParam(value = "company_id") Long companyId) {
        return new ResponseEntity<>(ratingService.getCompanyById(companyId), HttpStatus.OK);
    }

    @GetMapping(value = "/getRatingByUserId")
    public ResponseEntity<?> getUserById(
            @RequestParam(value = "user_id") Long userId) {
        return new ResponseEntity<>(ratingService.getUserById(userId), HttpStatus.OK);
    }

}
