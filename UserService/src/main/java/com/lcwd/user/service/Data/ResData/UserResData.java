package com.lcwd.user.service.Data.ResData;

import com.lcwd.user.service.Data.RatingResData;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResData {

    private Long userId;
    private String userName;
    private String email;
    private String about;
    private List<RatingResData> raringList;
}
