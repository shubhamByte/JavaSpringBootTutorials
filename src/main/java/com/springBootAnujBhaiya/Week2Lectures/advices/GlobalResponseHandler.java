package com.springBootAnujBhaiya.Week2Lectures.advices;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
        // Do not wrap springdoc endpoints (/v3/api-docs) or any other endpoints from org.springdoc
        String packageName = returnType.getContainingClass().getPackageName();
        return !packageName.startsWith("org.springdoc");
    }

    // whichever body comes(even errors), if it is not the apiResponse, it will convert it into apiResponse
    // and the body will be treated as data by default.
    // this acts at last at any type of responsebody(error, dto etc.)
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(body instanceof ApiResponse<?>) {
            return body;
        }

        return new ApiResponse<>(body);
    }
}
