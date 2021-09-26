package com.codegym.service.employee;

import com.codegym.model.Boss;
import com.codegym.model.Employee;
import com.codegym.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IEmployeeService extends IService<Employee> {
    Iterable<Employee> findAllByBoss(Boss boss);
    Page<Employee> findAllByNameContaining (String name, Pageable pageable);
    Page<Employee> findAll(Pageable pageable);
    Iterable<Employee> findAllByProducerContaining(String name);
}
