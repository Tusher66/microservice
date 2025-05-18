package com.ratting.service.RatingService.Repository;

import com.ratting.service.RatingService.Model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating,Long> {
}
