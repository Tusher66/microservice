package com.lcwd.user.service.Data.ResData;

import lombok.*;

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
}
