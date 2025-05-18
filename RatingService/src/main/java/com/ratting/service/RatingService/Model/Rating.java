package com.ratting.service.RatingService.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("user_rating")

public class Rating {

    @Id
    private String ratingId;

    private Long hotelId;
    private Long userId;
    private Long rating;
    private String feedback;
    private LocalDateTime insertDate;
}
