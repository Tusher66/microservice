package com.lcwd.user.service.Controller;


import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Data.ResData.ResponseBaseStatusData;
import com.lcwd.user.service.Data.ResData.ResponseSuccessData;
import com.lcwd.user.service.Model.users;
import com.lcwd.user.service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> saveUser(@RequestBody UserReqData userReqData){
        ResponseBaseStatusData user = userService.saveUser(userReqData);
        /*return (ResponseEntity) ResponseEntity.status(HttpStatus.OK);*/
        return new ResponseEntity<>(new ResponseSuccessData<>(user),HttpStatus.OK);
    }
}
