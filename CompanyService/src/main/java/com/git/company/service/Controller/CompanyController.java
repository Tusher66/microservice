package com.git.company.service.Controller;


import com.git.company.service.Data.ReqData.CompanyReqData;
import com.git.company.service.Data.ResData.ResponseBaseStatusData;
import com.git.company.service.Data.ResData.ResponseSuccessData;
import com.git.company.service.Service.CompanyService;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "/saveCompany")
    public ResponseEntity<?> saveUser(@RequestBody CompanyReqData companyReqData) {
        ResponseBaseStatusData user = companyService.saveCompany(companyReqData);
        return new ResponseEntity<>(new ResponseSuccessData<>(user), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllCompany")
    public ResponseEntity<?> getAllCompany(
            @RequestParam(value = "per_page", required = false, defaultValue = "10") int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @Nullable @RequestParam(value = "sort_by", required = false, defaultValue = "insert_date") String sortBy,
            @Nullable @RequestParam(value = "sort_type", required = false, defaultValue = "desc") String sortType,
            @Nullable @RequestParam(value = "search", required = false) Long search) {
        return new ResponseEntity<>(companyService.getAllCompanyData(page, size, sortBy, sortType, search), HttpStatus.OK);
    }

    @GetMapping(value = "/getCompanyById")
    public ResponseEntity<?> getCompanyById(
            @RequestParam(value = "company_id") Long companyId) {
        return new ResponseEntity<>(companyService.getCompanyDataById(companyId), HttpStatus.OK);
    }
}
