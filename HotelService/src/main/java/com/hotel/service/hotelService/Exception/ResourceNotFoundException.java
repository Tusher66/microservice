package com.hotel.service.hotelService.Exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource is not found!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
