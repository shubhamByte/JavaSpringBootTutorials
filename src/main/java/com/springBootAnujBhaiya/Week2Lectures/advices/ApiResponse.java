package com.springBootAnujBhaiya.Week2Lectures.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {


    @JsonFormat(pattern = "hh:mm:ss  dd-MM-yyyy")
    private LocalDateTime timeStamp;

    private T data;
    private ApiError errors;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {

        // this method helps in calling multiple constructor.
        this();
        this.data = data;
    }

    public ApiResponse(ApiError errors) {
        this();
        this.errors = errors;
    }
}
