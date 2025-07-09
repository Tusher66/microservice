package com.git.company.service.ServiceImpl;

import com.git.company.service.Data.ReqData.CompanyReqData;
import com.git.company.service.Data.ResData.*;
import com.git.company.service.Exception.CrudException;
import com.git.company.service.Model.Company;
import com.git.company.service.Repository.CompanyRepository;
import com.git.company.service.Service.CompanyService;
import com.git.company.service.Utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public ResponseBaseStatusData saveCompany(CompanyReqData companyReqData) {

        try{
            Company company = Company.builder()
                    .name(companyReqData.getName())
                    .about(companyReqData.getAbout())
                    .insertDate(LocalDateTime.now())
                    .location(companyReqData.getLocation())
                    .build();
            companyRepository.save(company);

            return ResponseBaseStatusData.builder()
                    .status(true)
                    .code(1)
                    .message("SUCCESSFULLY SAVED.")
                    .build();
        } catch (Exception e){
            throw new CrudException("AN UNEXPECTED ERROR OCCURRED.", e.getMessage());
        }

    }

    @Override
    public PaginatedResData getAllCompanyData(int page, int size, String sortBy, String sortType, Long search) {

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "insert_date";
            sortType = "desc";
        }

        if (sortType == null || sortType.isEmpty()) {
            sortType = "desc";
        }

        Pageable pageRequest = PageUtils.generatePageRequest((page - 1), size, sortBy, sortType);
        Page<Company> pageResult = null;

        pageResult = companyRepository.findAllCompany(search, pageRequest);

        List<CompanyResData> result = pageResult.stream().map(this::companyListResDataMap).toList();

        RequestBaseData request = RequestBaseData.builder()
                .perPage(size)
                .page(page)
                .sortBy(sortBy)
                .sortType(sortType)
                .build();

        return PageUtils.getPaginatedResData(page, size, request, pageRequest,
                pageResult.getTotalElements(), pageResult.getTotalPages(), result);
    }
    private CompanyResData companyListResDataMap(Company company) {

        return CompanyResData.builder()
                .companyId(company.getCompanyId())
                .name(company.getName())
                .about(company.getAbout())
                .location(company.getLocation())
                .build();
    }

    @Override
    public ResponseBaseData getCompanyDataById(Long companyId) {
        Optional<Company> UserData = companyRepository.findById(companyId);
        return ResponseBaseData.builder()
                .status(true)
                .code(1)
                .message("Data Found")
                .data(UserData)
                .build();
    }

}
