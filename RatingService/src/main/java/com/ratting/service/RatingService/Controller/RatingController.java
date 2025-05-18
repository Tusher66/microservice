package com.ratting.service.RatingService.Controller;


import com.ratting.service.RatingService.Data.ReqData.RatingReqData;
import com.ratting.service.RatingService.Data.ResData.ResponseBaseStatusData;
import com.ratting.service.RatingService.Data.ResData.ResponseSuccessData;
import com.ratting.service.RatingService.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping(value = "/saveRating")
    public ResponseEntity<?> saveUser(@RequestBody RatingReqData ratingReqData){
        ResponseBaseStatusData user = ratingService.saveRating(ratingReqData);
        return new ResponseEntity<>(new ResponseSuccessData<>(user), HttpStatus.OK);
    }

}
