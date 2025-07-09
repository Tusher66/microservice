package com.lcwd.user.service.Data.ResData;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Company {
    private Long companyId;
    private String name;
    private LocalDateTime insertDate;
    private String about;
    private String location;
}