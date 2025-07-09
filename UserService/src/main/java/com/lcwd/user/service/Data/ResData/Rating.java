package com.lcwd.user.service.Data.ResData;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    private String ratingId;
    private Long companyId;
    private Long userId;
    private Long rating;
    private String feedback;
    private LocalDateTime insertDate;
    private Company company;
}
