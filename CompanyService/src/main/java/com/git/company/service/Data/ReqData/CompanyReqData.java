package com.git.company.service.Data.ReqData;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CompanyReqData {

    private Long companyId;
    private String name;
    private String about;
    private String location;
}
