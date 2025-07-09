package com.git.company.service.Service;


import com.git.company.service.Data.ReqData.CompanyReqData;
import com.git.company.service.Data.ResData.PaginatedResData;
import com.git.company.service.Data.ResData.ResponseBaseData;
import com.git.company.service.Data.ResData.ResponseBaseStatusData;

public interface CompanyService {

    ResponseBaseStatusData saveCompany(CompanyReqData companyReqData);

    PaginatedResData<?> getAllCompanyData(int page, int size, String sortBy, String sortType, Long search);

    ResponseBaseData getCompanyDataById(Long companyId);
}
