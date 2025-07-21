package com.lcwd.user.service.ServiceImpl;

import com.lcwd.user.service.Data.ReqData.UserReqData;
import com.lcwd.user.service.Data.ResData.*;
import com.lcwd.user.service.Exception.CrudException;
import com.lcwd.user.service.Exception.ResourceNotFoundException;
import com.lcwd.user.service.External.Service.CompanyService;
import com.lcwd.user.service.Model.users;
import com.lcwd.user.service.Repository.UserRepository;
import com.lcwd.user.service.Service.UserService;
import com.lcwd.user.service.Utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseBaseStatusData saveUser(UserReqData userReqData) {

        try {
            users User = users.builder()
                    .userName(userReqData.getUserName())
                    .about(userReqData.getAbout())
                    .email(userReqData.getEmail())
                    .insertDate(LocalDateTime.now())
                    .password(userReqData.getPassword())
                    .build();
            userRepository.save(User);

            return ResponseBaseStatusData.builder()
                    .status(true)
                    .code(1)
                    .message("SUCCESSFULLY SAVED.")
                    .build();
        } catch (Exception e) {
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
        users userData = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
        String url = UriComponentsBuilder.fromHttpUrl("http://RATINGSERVICE/rating/getRatingByUserId")
                .queryParam("user_id", userId)
                .toUriString();

        ResponseEntity<RatingsResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RatingsResponse>() {
                }
        );

        List<Rating> ratings = response.getBody().getData();

        List<Rating> ratingWithCompany = ratings.stream().map(rating -> {
            CompanyResponse companyResponse = companyService.getCompany(rating.getCompanyId());
            Company company = companyResponse.getData();
            rating.setCompany(company);

            rating.setCompany(company);

            return rating;
        }).collect(Collectors.toList());

        userData.setRatings(ratingWithCompany);

        return ResponseBaseData.builder()
                .status(true)
                .code(1)
                .message("Data Found")
                .data(userData)
                .build();
    }

    @Override
    public ResponseBaseData getUserByUserName(String userName) {
        Optional<users> userData = Optional.ofNullable(userRepository.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("User Not Found!")));

        return ResponseBaseData.builder()
                .status(true)
                .code(1)
                .message("Data Found")
                .data(userData)
                .build();
    }
}
