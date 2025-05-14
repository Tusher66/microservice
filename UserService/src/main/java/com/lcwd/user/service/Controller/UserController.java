package com.lcwd.user.service.Controller;


import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Data.ResData.ResponseBaseStatusData;
import com.lcwd.user.service.Data.ResData.ResponseSuccessData;
import com.lcwd.user.service.Model.users;
import com.lcwd.user.service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> saveUser(@RequestBody UserReqData userReqData){
        ResponseBaseStatusData user = userService.saveUser(userReqData);
        return new ResponseEntity<>(new ResponseSuccessData<>(user),HttpStatus.OK);
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(value = "per_page", required = false, defaultValue = "10") int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @Nullable @RequestParam(value = "sort_by", required = false, defaultValue = "insert_date") String sortBy,
            @Nullable @RequestParam(value = "sort_type", required = false, defaultValue = "desc") String sortType,
            @Nullable @RequestParam(value = "search", required = false) Long search) {
        return new ResponseEntity<>(userService.getAllPagedUsersData(page, size, sortBy, sortType, search), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserById")
    public ResponseEntity<?> getUserById(
            @RequestParam(value = "user_id") Long userId) {
        return new ResponseEntity<>(userService.getUserDataById(userId), HttpStatus.OK);
    }
}
