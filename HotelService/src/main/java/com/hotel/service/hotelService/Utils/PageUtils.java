package com.hotel.service.hotelService.Utils;

import com.hotel.service.hotelService.Data.ResData.NewPaginationBaseData;
import com.hotel.service.hotelService.Data.ResData.PaginatedResData;
import com.hotel.service.hotelService.Data.ResData.PaginationBaseData;
import com.hotel.service.hotelService.Data.ResData.RequestBaseData;
import com.hotel.service.hotelService.Exception.ArgumentNotValidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageUtils {

    /**
     * validatePageParams
     *
     * @param page
     * @param size
     * @param sortBy
     * @param sortType
     */
    public static void validatePageParams(int page, int size, String sortBy, String sortType) {
        if (page < 0) {
            throw new ArgumentNotValidException("INVALID PAGE NUMBER!");
        }

        if (size < 1) {
            throw new ArgumentNotValidException("PAGE SIZE CAN NOT BE LESS THAN 1.");
        }

        if (sortBy == null || sortBy.isEmpty()) {
            throw new ArgumentNotValidException("SORT BY CAN NOT BE EMPTY!");
        }

        if (sortType == null || sortType.isEmpty()) {
            throw new ArgumentNotValidException("SORT TYPE CAN NOT BE EMPTY!");
        }
    }

    /**
     * page = php::page & size = php::perPage
     *
     * @param page
     * @param size
     * @param sortBy
     * @param sortType
     * @return
     */
    public static Pageable generatePageRequestWithoutCamelCase(int page, int size, String sortBy, String sortType) {
        validatePageParams(page, size, sortBy, sortType);
        Pageable pageRequest;

        if (sortType.equalsIgnoreCase("asc")) {
            pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else if (sortType.equalsIgnoreCase("desc")) {
            pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            throw new ArgumentNotValidException("INVALID SORT TYPE!");
        }
        return pageRequest;

    }

    /**
     * page = php::page & size = php::perPage
     *
     * @param page
     * @param size
     * @param sortBy
     * @param sortType
     * @return
     */
    public static Pageable generatePageRequest(int page, int size, String sortBy, String sortType) {
        validatePageParams(page, size, sortBy, sortType);
        Pageable pageRequest;

        if (sortType.equalsIgnoreCase("asc")) {
            pageRequest = PageRequest.of(page, size, Sort.by(StringUtils.underscoreToCamelCase(sortBy)).ascending());
        } else if (sortType.equalsIgnoreCase("desc")) {
            pageRequest = PageRequest.of(page, size, Sort.by(StringUtils.underscoreToCamelCase(sortBy)).descending());
        } else {
            throw new ArgumentNotValidException("INVALID SORT TYPE!");
        }
        return pageRequest;

    }

    /**
     * page = php::page & size = php::perPage
     * for joinColumn
     *
     * @param page
     * @param size
     * @param sortBy
     * @param sortType
     * @param joined
     * @return
     */
    public static Pageable generatePageRequest(int page, int size, String sortBy, String sortType, boolean joined) {
        validatePageParams(page, size, sortBy, sortType);
        String[] sortByArr = sortBy.split("\\.");

        if (sortByArr.length == 2 && joined) {
            String sortByClassName = sortByArr[0];
            String sortByPropertyName = sortByArr[1];

            Pageable pageRequest = PageRequest.of(0, 1);
            if (sortType.equalsIgnoreCase("asc")) {
                pageRequest = PageRequest.of(page, size, Sort.by(sortByClassName + "." + StringUtils.underscoreToCamelCase(sortByPropertyName)).ascending());
            } else if (sortType.equalsIgnoreCase("desc")) {
                pageRequest = PageRequest.of(page, size, Sort.by(sortByClassName + "." + StringUtils.underscoreToCamelCase(sortByPropertyName)).descending());
            }
            return pageRequest;
        } else {
            throw new ArgumentNotValidException("INVALID PARAMETER!");
        }

    }

    public static int getFromElementRowNum(int page, int size) {
        return (page * size) - (size - 1);
    }

    public static int getToElementRowNum(int page, int size, long total) {
        long to = (long) page * size;
        if (to > total) {
            to = total;
        }
        // return page * size;
        return (int) to;
    }

    public static PaginatedResData<?> getPaginatedResData(int page, int size, RequestBaseData requestBaseData,
                                                          Pageable pageRequest, Page<?> pageResult) {
        return getPaginatedResData(page, size, requestBaseData, pageRequest, pageResult.getTotalElements(),
                pageResult.getTotalPages(), pageResult.getContent());
    }

    public static PaginatedResData<?> getPaginatedResData(int page, int size, RequestBaseData requestBaseData, Pageable pageRequest,
                                                          Long totalElements, Integer totalPages, List<?> content) {
        PaginationBaseData<?> paginationBaseData = PaginationBaseData.builder()
                .total(totalElements)
                .perPage(pageRequest.getPageSize())
                .currentPage(pageRequest.getPageNumber() + 1)
                .from(getFromElementRowNum(page, size))
                .to(getToElementRowNum(page, size, totalElements))
                .lastPage(totalPages)
                .data(content)
                .build();

        PaginatedResData paginatedResData = new PaginatedResData<>();
        paginatedResData.setStatus(true);
        paginatedResData.setCode(1);
        paginatedResData.setMessage(totalElements > 0 ? "DATA FOUND" : "DATA NOT FOUND");
        paginatedResData.setData(paginationBaseData);
        paginatedResData.setRequest(requestBaseData);
        return paginatedResData;
    }

    public static <T> NewPaginationBaseData<T> getPaginationBaseData(
            Page<T> result, Pageable pageRequest) {

        int page = result.getPageable().getPageNumber();
        int size = result.getPageable().getPageSize();
        long totalElements = result.getTotalElements();
        int totalPages = result.getTotalPages();
        List<T> content = result.getContent();

        return NewPaginationBaseData.<T>builder()
                .total(totalElements)
                .perPage(size)
                .currentPage(pageRequest.getPageNumber())
                .from(getFromElementRowNum(page + 1, size) - 1)
                .to(getToElementRowNum(page + 1, size, totalElements) - 1)
                .lastPage(totalPages - 1)
                .data(content)
                .build();
    }
}
