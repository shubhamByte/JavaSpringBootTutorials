package com.springBootAnujBhaiya.Week2Lectures.dto;

// dto dont have any annotations or imports. its just plain java object.

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String name;
    private Integer age;
    private String address;
    private Boolean isActive;
}
