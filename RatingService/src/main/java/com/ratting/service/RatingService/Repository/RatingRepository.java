package com.ratting.service.RatingService.Repository;

import com.ratting.service.RatingService.Model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends MongoRepository<Rating,Long> {

    @Query("SELECT r FROM Rating r WHERE  (:search IS NULL OR h.userId= :search)")
    Page<Rating> findAllRating(@Param("search") Long search, Pageable pageable);

}
