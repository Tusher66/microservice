package com.hotel.service.HotelService.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
@Builder

public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hotel_id", nullable = false)
    private Long hotelId;

    @Column(name = "name")
    private String name;

    @Column(name = "about")
    private String about;

    @Column(name = "location")
    private String location;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;


}
