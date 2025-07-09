package com.git.company.service.Repository;


import com.git.company.service.Model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT h FROM Company h WHERE  (:search IS NULL OR h.name= :search)")
    Page<Company> findAllCompany(@Param("search") Long search, Pageable pageable);
}
