package com.lcwd.user.service.ServiceImpl;

import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Model.users;
import com.lcwd.user.service.Repository.UserRepository;
import com.lcwd.user.service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public users saveUser(UserReqData userReqData) {

        users User = users.builder()
                .userName(userReqData.getUserName())
                .about(userReqData.getAbout())
                .email(userReqData.getEmail())
                .build();
        return userRepository.save(User);
    }
}
