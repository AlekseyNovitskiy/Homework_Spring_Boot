package com.skypro.homework_spring_boot;

import com.skypro.homework_spring_boot.exception.EmployeeNotFoundException;
import com.skypro.homework_spring_boot.model.Employee;
import com.skypro.homework_spring_boot.record.EmployeeRequest;
import com.skypro.homework_spring_boot.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {
    private EmployeeService employeeService;


    @BeforeEach
    public void setUp() {
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("Ivanov", "Ivan",1,35000),
                new EmployeeRequest("Petrov","Petr",2,47000),
                new EmployeeRequest("Bazhenov","Valentin",3,52000),
                new EmployeeRequest("Belkin","Sergey",1,33000),
                new EmployeeRequest("Voroshilov","Maksim",3,102000),
                new EmployeeRequest("Slezko","Petr",2,44000)
        ).forEach(employeeService::addEmployee);
    }

    @Test
    public void addEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "Sidorenco", "Aleksandr", 4, 138000);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());
        Assertions.assertThat(employeeService.getAllEmployees()).contains(result);
    }

    @Test
    public void listEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(6);
        Assertions.assertThat(employees).last().extracting(Employee::getFirstName)
                .isEqualTo("Ivanov");
    }

    @Test
    public void sumOfSalaries() {
        int sum = employeeService.getSalarySum();
        Assertions.assertThat(sum).isEqualTo(313000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.getSalaryMax();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName)
                .isEqualTo("Voroshilov");
    }
    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.getSalaryMin();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName)
                .isEqualTo("Belkin");
    }


    @Test
    public void removeEmployee() {
        employeeService.removeEmployee(employeeService.getAllEmployees().iterator().next().getId());
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
    }



}
