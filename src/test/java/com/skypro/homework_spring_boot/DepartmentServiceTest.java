package com.skypro.homework_spring_boot;

import com.skypro.homework_spring_boot.exception.EmployeeNotFoundException;
import com.skypro.homework_spring_boot.model.Employee;
import com.skypro.homework_spring_boot.record.EmployeeRequest;
import com.skypro.homework_spring_boot.service.DepartmentService;
import com.skypro.homework_spring_boot.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("Ivanov", "Ivan", 1, 35000),
            new Employee("Petrov", "Petr", 2, 47000),
            new Employee("Bazhenov", "Valentin", 3, 52000),
            new Employee("Belkin", "Sergey", 1, 33000),
            new Employee("Voroshilov", "Maksim", 3, 102000),
            new Employee("Slezko", "Petr", 2, 44000));
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        when(employeeService.getAllEmployees()).thenReturn(employees);
    }
    @Test
    void getEmployeesByDepartment() {
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        assertThat(employeeList).hasSize(2).contains(employees.get(0), employees.get(3));
    }

    @Test
    void sumOfSalariesByDepartment() {
        int sum = this.departmentService.getSumOfSalariesInDepartment(1);
        assertThat(sum).isEqualTo(68000);
    }

    @Test
    void maxSalaryInDepartment() {
        int max = this.departmentService.getMaxSalaryInDepartment(3);
        assertThat(max).isEqualTo(102000);
    }
    @Test
    void minSalaryInDepartment() {
        int min = this.departmentService.getMinSalaryInDepartment(2);
        assertThat(min).isEqualTo(44000);
    }

    @Test
    void groupedEmployees() {
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartments();
        assertThat(groupedEmployees).hasSize(3)
                .containsEntry(1, List.of(employees.get(0), employees.get(3)))
                .containsEntry(2, List.of(employees.get(1), employees.get(5)))
                .containsEntry(3, List.of(employees.get(2), employees.get(4)));
    }

    @Test
    void whenNoEmployeesThenGroupByReturnEmptyMap() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartments();
        assertThat(groupedEmployees).isEmpty();
    }

    @Test
    void whenNoEmployeesThenMaxSalaryInDepartmentThrowsException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> departmentService
                .getMaxSalaryInDepartment(0)).isInstanceOf(EmployeeNotFoundException.class);
    }

}
