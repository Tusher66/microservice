package com.lcwd.user.service.Service;

import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Data.ResData.ResponseBaseStatusData;
import com.lcwd.user.service.Model.users;

public interface UserService {

 ResponseBaseStatusData saveUser(UserReqData userReqData);
}
