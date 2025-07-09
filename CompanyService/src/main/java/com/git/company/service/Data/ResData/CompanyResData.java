package com.git.company.service.Data.ResData;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CompanyResData {
    private Long companyId;
    private String name;
    private String about;
    private String location;
}
