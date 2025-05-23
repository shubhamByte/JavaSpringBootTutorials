package com.springBootAnujBhaiya.Week2Lectures.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// don't use hashcode and equalto from lombok that crates problem with object which is rdbms.
// if you want to define them by yourself.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity         // this creates a corresponding table
@Table(name = "employees")
public class EmployeeEntity {

    // makes id variable primary key of the table
    // hibernate creates corresponding table in the database. this entity is java object and not table.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer age;
    private String role;
    private Double  salary;
    private LocalDate dateOfJoining;
    private String email;
    private String address;
    private Boolean isActive;
}
