package com.hemendrasahu.userservice.exceptions;

import com.hemendrasahu.userservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateEntryException(DuplicateEntryException duplicateEntryException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.FORBIDDEN, duplicateEntryException.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ExceptionDto> handleInvalidInputException(InvalidInputException invalidInputException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, invalidInputException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
}