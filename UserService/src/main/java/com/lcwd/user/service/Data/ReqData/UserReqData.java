package com.lcwd.user.service.Data.ReqData;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserReqData {

    private Long userId;
    private String userName;
    private String email;
    private String about;
    private String password;
}
