package com.lcwd.user.service.Data;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResData {

    private String ratingId;
    private Long companyId;
    private Long userId;
    private Long rating;
    private String feedback;
    private LocalDateTime insertDate;
}
