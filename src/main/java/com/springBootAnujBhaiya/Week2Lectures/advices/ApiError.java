package com.springBootAnujBhaiya.Week2Lectures.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;


@Builder
@Data
public class ApiError {

    private HttpStatus status;
    private String errors;
    private List<String> subErrors;
}
