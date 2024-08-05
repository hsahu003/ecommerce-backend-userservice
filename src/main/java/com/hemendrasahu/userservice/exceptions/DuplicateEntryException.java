package com.hemendrasahu.userservice.exceptions;

public class DuplicateEntryException extends Exception{
    public DuplicateEntryException(String message){
        super(message);
    }
}