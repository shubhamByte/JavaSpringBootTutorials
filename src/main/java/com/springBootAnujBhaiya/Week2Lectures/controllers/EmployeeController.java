package com.springBootAnujBhaiya.Week2Lectures.controllers;

import com.springBootAnujBhaiya.Week2Lectures.dto.EmployeeDTO;
import com.springBootAnujBhaiya.Week2Lectures.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/employee")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id){

        Optional<EmployeeDTO> employeeDTO =  employeeService.getEmployeeById(id);

        return employeeDTO
                .map( employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());

        // use map this way if you want ki agar null nahi hai to this otherwise orElse.
    }


    @GetMapping
    public ResponseEntity< List<EmployeeDTO> > getAllEmployees(@RequestParam(required = false) Integer age,
                                             @RequestParam(required = false, name = "sortBy") String orderOfSorting) {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping("/newEmployee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO createdEmployee = employeeService.createNewEmployee(inputEmployee);
        // if you want to give some other code (apart from 404 or 200)
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable Long id, @RequestBody EmployeeDTO updatedEmployeeDTO) {

        return ResponseEntity.ok(employeeService.updateEmployeeById(id, updatedEmployeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id){
        Boolean gotDeleted = employeeService.deleteEmployeeById(id);
        if(gotDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> partialUpdatesEmployeeById(@PathVariable Long id,
                                                 @RequestBody Map<String, Object> updatesMap){

        EmployeeDTO updatedEmployeeDTO =  employeeService.partialUpdatesEmployeeById(id, updatesMap);
        if(updatedEmployeeDTO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(updatedEmployeeDTO);
    }

}
