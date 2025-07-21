package com.lcwd.user.service.Controller;


import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Data.ResData.ResponseBaseStatusData;
import com.lcwd.user.service.Data.ResData.ResponseSuccessData;
import com.lcwd.user.service.Data.ResData.UserResData;
import com.lcwd.user.service.Service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
    public ResponseEntity<?> saveUser(@RequestBody UserReqData userReqData) {
        ResponseBaseStatusData user = userService.saveUser(userReqData);
        return new ResponseEntity<>(new ResponseSuccessData<>(user), HttpStatus.OK);
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

    int retryCount = 1;

    @GetMapping(value = "/getUserById")
    //@CircuitBreaker(name = "ratingCompanyBreaker", fallbackMethod = "ratingCompanyFallBack")
    //@Retry(name="ratingCompanyService", fallbackMethod = "ratingCompanyFallBack")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingCompanyFallBack")
    public ResponseEntity<?> getUserById(
            @RequestParam(value = "user_id") Long userId) {
        System.out.println("Retry Count" + retryCount);
        retryCount++;
        return new ResponseEntity<>(userService.getUserDataById(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserByUserName")
    public ResponseEntity<?> getUserByUserName(
            @RequestParam(value = "user_name") String userName) {
        return new ResponseEntity<>(userService.getUserByUserName(userName), HttpStatus.OK);
    }

    public ResponseEntity<?> ratingCompanyFallBack(Long userId, Exception ex) {
        System.out.println("Fall Back Method is running because service is down" + ex.getMessage());
        UserResData userResData = UserResData.builder()
                .userId(null)
                .userName("Dummy")
                .email("Dummy@gmail.com")
                .about("This is Dummy Becouse Service is down")
                .build();
        return new ResponseEntity<>(userResData, HttpStatus.OK);
    }
}
