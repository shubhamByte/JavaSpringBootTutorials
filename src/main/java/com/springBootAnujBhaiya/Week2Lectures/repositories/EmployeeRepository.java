package com.springBootAnujBhaiya.Week2Lectures.repositories;

import com.springBootAnujBhaiya.Week2Lectures.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// even though it is interface, it creates its own implementation during runtime and become bean.
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
//    List<EmployeeEntity> findByAddress() ;
}
