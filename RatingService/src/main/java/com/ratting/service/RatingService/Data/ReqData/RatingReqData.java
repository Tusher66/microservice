package com.ratting.service.RatingService.Data.ReqData;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingReqData {

    private String ratingId;
    private Long companyId;
    private Long userId;
    private Long rating;
    private String feedback;
    private LocalDateTime insertDate;
}
