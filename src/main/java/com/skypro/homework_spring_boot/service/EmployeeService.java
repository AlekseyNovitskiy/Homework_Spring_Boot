package com.skypro.homework_spring_boot.service;

import com.skypro.homework_spring_boot.model.Employee;
import com.skypro.homework_spring_boot.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();


    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee name should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(),
                employeeRequest.getDepartment(), employeeRequest.getSalary());
        this.employees.put(employee.getId(), employee);
        return employee;

    }

    public int getSalarySum() {
        return employees.values().stream().mapToInt(Employee::getSalary).sum();
    }

    public Employee getSalaryMin() {
        return employees.values().stream().min(Comparator.comparingInt(Employee::getSalary)).get();
    }

    public Employee getSalaryMax() {
        return employees.values().stream().max(Comparator.comparingInt(Employee::getSalary)).get();
    }

    public int getSalaryAverage() {
        return getSalarySum() / employees.size();
    }

    public Set<Employee> getHighSalary() {
        int averageSalary = getSalaryAverage();
        return employees.values().stream().filter(e -> e.getSalary() > averageSalary).collect(Collectors.toSet());
    }
}
