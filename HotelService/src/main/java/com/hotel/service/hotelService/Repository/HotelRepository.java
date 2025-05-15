package com.hotel.service.hotelService.Repository;

import com.hotel.service.hotelService.Model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE  (:search IS NULL OR h.name= :search)")
    Page<Hotel> findAllHotel(@Param("search") Long search, Pageable pageable);
}
