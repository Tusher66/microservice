package com.lcwd.user.service.External.Service;

import com.lcwd.user.service.Data.ResData.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "COMPANYSERVICE")
public interface CompanyService {

    @GetMapping("/company/getCompanyById")
    CompanyResponse getCompany(@RequestParam(value = "company_id") Long companyId);

}
