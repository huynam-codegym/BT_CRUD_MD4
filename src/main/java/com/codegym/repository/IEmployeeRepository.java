package com.codegym.repository;

import com.codegym.model.Boss;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IEmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    Iterable<Employee> findAllByBoss(Boss boss);

    Page<Employee> findAllByNameContaining (String name, Pageable pageable);
    Page<Employee> findAll(Pageable pageable);

    Iterable<Employee> findAllByNameContaining(String name);
}
