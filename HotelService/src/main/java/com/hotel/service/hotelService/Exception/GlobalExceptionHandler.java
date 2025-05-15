package com.hotel.service.hotelService.Exception;


import com.lcwd.user.service.Payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse response = ApiResponse.builder()
                .message(message)
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    // handling crud exception
    @ExceptionHandler(CrudException.class)
    public ResponseEntity<?> crudExceptionHandler(CrudException ex, WebRequest request) {
        System.out.println("\n" + "CrudException Occurred at: " + LocalDateTime.now());
        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handling argumentNotValid exception
    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValidHandling(ArgumentNotValidException exception, WebRequest request) {
        System.out.println("\n" + "ArgumentNotValidException Occurred at: " + LocalDateTime.now());
        exception.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), exception.getErrorDetails());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
