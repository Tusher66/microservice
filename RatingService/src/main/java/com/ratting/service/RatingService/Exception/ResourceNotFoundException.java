package com.ratting.service.RatingService.Exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource is not found!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
