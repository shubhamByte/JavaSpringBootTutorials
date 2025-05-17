package com.springBootAnujBhaiya.Week2Lectures.advices;

import com.springBootAnujBhaiya.Week2Lectures.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/*
 we are not directly returning apiError because Global responseHandler will treat the apiError
as data of the apiResponse while wrapping up at last stage. we want to make sure that apiError is treated
as error in apiResponse. so we are creating apiResponse manually in the GlobalException Handler.
But Controller responseDTO treated as data in apiresponse has no such problem.
 */

/*
In other words.
The GlobalExceptionHandler manually wraps the ApiError in an ApiResponse so that, later,
GlobalResponseHandler does not wrap it again. This ensures that error details are placed
in the ApiResponse's errors field, while controller responses are treated as data.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException nSEE){

        ApiError apiError = ApiError.builder()
                .errors(nSEE.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllExceptions(Exception e){

        ApiError apiError = ApiError.builder()
                .errors(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationErrorDTO(MethodArgumentNotValidException mANVE){

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

        return buildErrorResponseEntity(apiError);
    }


    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {

        return ResponseEntity
                .status(apiError.getStatus())
                .body(new ApiResponse<>(apiError));
//        return new ResponseEntity<>("no such resource found", HttpStatus.NOT_FOUND);
    }

}
