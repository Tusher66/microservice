package com.lcwd.user.service.ServiceImpl;

import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Data.ResData.*;
import com.lcwd.user.service.Exception.CrudException;
import com.lcwd.user.service.Model.users;
import com.lcwd.user.service.Repository.UserRepository;
import com.lcwd.user.service.Service.UserService;
import com.lcwd.user.service.Utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseBaseStatusData saveUser(UserReqData userReqData) {

        try{
            users User = users.builder()
                    .userName(userReqData.getUserName())
                    .about(userReqData.getAbout())
                    .email(userReqData.getEmail())
                    .insertDate(LocalDateTime.now())
                    .build();
            userRepository.save(User);

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
    public PaginatedResData getAllPagedUsersData(int page, int size, String sortBy, String sortType, Long search) {

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "insert_date";
            sortType = "desc";
        }

        if (sortType == null || sortType.isEmpty()) {
            sortType = "desc";
        }

        Pageable pageRequest = PageUtils.generatePageRequest((page - 1), size, sortBy, sortType);
        Page<users> pageResult = null;

        pageResult = userRepository.findAllUsers(search, pageRequest);

        List<UserResData> result = pageResult.stream().map(this::userListResDataMap).toList();

        RequestBaseData request = RequestBaseData.builder()
                .perPage(size)
                .page(page)
                .sortBy(sortBy)
                .sortType(sortType)
                .build();

        return PageUtils.getPaginatedResData(page, size, request, pageRequest,
                pageResult.getTotalElements(), pageResult.getTotalPages(), result);
    }

    private UserResData userListResDataMap(users Users) {

        return UserResData.builder()
                .userId(Users.getUserId())
                .userName(Users.getUserName())
                .about(Users.getAbout())
                .email(Users.getEmail())
                .build();
    }

    @Override
    public ResponseBaseData getUserDataById(Long userId) {
        Optional<users> UserData = userRepository.findById(userId);
        return ResponseBaseData.builder()
                .status(true)
                .code(1)
                .message("Data Found")
                .data(UserData)
                .build();
    }
}
