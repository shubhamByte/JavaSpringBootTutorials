package com.springBootAnujBhaiya.Week2Lectures.dto;

// dto dont have any annotations or imports. its just plain java object.

import com.springBootAnujBhaiya.Week2Lectures.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    // for strings -> notBlank is perfect.
    @NotBlank(message = "Name of the employee cant be blank")
    @Size(min = 3, max = 15, message = "Size of name should be in between 3 to 15")
    private String name;

    @Email(message = "Email should be a valid email")
    private String email;

    @Max(value = 80, message = "age should be less than equal to 80")
    @Min(value = 18, message = "age should be greater than equal to 18")
    private Integer age;

    @NotBlank(message = "role of the employee can't be blank")
//    @Pattern(regexp = "^(user|admin)$", message = "role should be either user or admin")
    @EmployeeRoleValidation
    private String role;

    @NotNull(message = "salary of the employee cant be null")
    @Positive(message = "salary should be greater than 0")
    @Digits(integer = 6, fraction = 2, message = "salary should be in the format XXXXXX.YY")
    @DecimalMax(value = "1000000.00")
    @DecimalMin(value = "100")
    private Double salary;

    @PastOrPresent(message = "date of joining cant be in future")
    private LocalDate dateOfJoining;

    private String address;

    @AssertTrue(message = "employee must be active")
    private Boolean isActive;
}
