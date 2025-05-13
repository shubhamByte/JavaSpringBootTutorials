package com.springBootAnujBhaiya.Week2Lectures.controllers;

import com.springBootAnujBhaiya.Week2Lectures.dto.EmployeeDTO;
import com.springBootAnujBhaiya.Week2Lectures.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/employee")
@RestController    // this includes controller and response body
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. Sending Data by Path variable
    // writing path is not necessary  you can directly give address
    // using name we can give different variable_name for the method than the original(url variable name).
    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id){

        // return new EmployeeDTO(id, "Shubham kumar", 24, "patna", true);
        Optional<EmployeeDTO> employeeDTO =  employeeService.getEmployeeById(id);

        return employeeDTO
                .map( employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());

        // use map this way if you want ki agar null nahi hai to this otherwise orElse.
    }


    // 2. sending data by requestparam (? &)
    // by default request param required is true. request param is used if passing parameter is not mandotary
    // @GetMapping()  -> will also work
    @GetMapping
    public ResponseEntity< List<EmployeeDTO> > getAllEmployees(@RequestParam(required = false) Integer age,
                                             @RequestParam(required = false, name = "sortBy") String orderOfSorting) {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // if same path, by default it is considered get.
    @PostMapping("/create")
    public String createEmployee() {
        return "Employee successfully created in database";
    }

    // 3. Sending data by requestBody
    // no change in url for requestbody type
    @PostMapping("/newEmployee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO inputEmployee) {
        EmployeeDTO createdEmployee = employeeService.createNewEmployee(inputEmployee);
        // if you want to give some other code (apart from 404 or 200)
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    // all those fields whose value are not passed but required is set to null automatically.
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



    // @RequestBody
    // @RequestMapping
    // @RequestParam

    // we create same name function in the service layer as it is in controller layer which calls on
    // repo instead of service layer.
    // this is only the convention not rule.
}
