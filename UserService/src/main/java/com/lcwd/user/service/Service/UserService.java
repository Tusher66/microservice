package com.lcwd.user.service.Service;

import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Data.ResData.PaginatedResData;
import com.lcwd.user.service.Data.ResData.ResponseBaseData;
import com.lcwd.user.service.Data.ResData.ResponseBaseStatusData;

public interface UserService {

    ResponseBaseStatusData saveUser(UserReqData userReqData);

    PaginatedResData<?> getAllPagedUsersData(int page, int size, String sortBy, String sortType, Long search);

    ResponseBaseData getUserDataById(Long userId);

    ResponseBaseData getUserByUserName(String userName);
}
