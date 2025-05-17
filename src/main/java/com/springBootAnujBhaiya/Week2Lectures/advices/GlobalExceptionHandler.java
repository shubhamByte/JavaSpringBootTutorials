package com.springBootAnujBhaiya.Week2Lectures.advices;

import com.springBootAnujBhaiya.Week2Lectures.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException nSEE){
//        return new ResponseEntity<>("no such resource found", HttpStatus.NOT_FOUND);

        ApiError apiError = ApiError.builder()
                .errors(nSEE.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception e){

        ApiError apiError = ApiError.builder()
                .errors(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrorDTO(MethodArgumentNotValidException mANVE){

        // creating list of all the validationError message.
        List<String> errorsList = mANVE.getBindingResult()
                .getAllErrors()
                .stream()
                .map(e-> e.getDefaultMessage())
                .collect(Collectors.toList());


        ApiError apiError = ApiError.builder()
                .errors("Validation Errors")
                .subErrors(errorsList)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

}
