package com.springBootAnujBhaiya.Week2Lectures.services;

import com.springBootAnujBhaiya.Week2Lectures.dto.EmployeeDTO;
import com.springBootAnujBhaiya.Week2Lectures.entities.EmployeeEntity;
import com.springBootAnujBhaiya.Week2Lectures.exceptions.ResourceNotFoundException;
import com.springBootAnujBhaiya.Week2Lectures.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {

        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);

        return employeeEntity.map(e -> modelMapper.map(e, EmployeeDTO.class));
    }

    public List<EmployeeDTO> findAll() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployeeDTO) {

        EmployeeEntity toSaveEntity =  modelMapper.map(inputEmployeeDTO, EmployeeEntity.class);
        EmployeeEntity savedEntity =  employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }


    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO updatedEmployeeDTO) {

        isExistEmployeeById(id);

        EmployeeEntity updatedEmployeeEntity =  modelMapper.map(updatedEmployeeDTO, EmployeeEntity.class);

        // tries to find the data with id. similaar to unordered_map key. if found updates. if not
        // found, crates new one.
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(updatedEmployeeEntity);

        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long id) {

        isExistEmployeeById(id);

        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO partialUpdatesEmployeeById(Long id, Map<String, Object> updatesMap) {

        isExistEmployeeById(id);

        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();

        updatesMap.forEach((key, value) -> {
            Field updatableField = ReflectionUtils.findRequiredField(EmployeeEntity.class, key);
            updatableField.setAccessible(true);
            // changes the value in the object(here entity)
            ReflectionUtils.setField(updatableField, employeeEntity, value);
        });
        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(updatedEmployeeEntity, EmployeeDTO.class);
    }

    public void isExistEmployeeById(Long id){
        if(!employeeRepository.existsById(id)) throw new ResourceNotFoundException("Resource with id " + id + " not found");
    }
}
