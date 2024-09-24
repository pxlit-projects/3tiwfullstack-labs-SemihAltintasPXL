package be.pxl.controller;

import be.pxl.domain.Employee;
import be.pxl.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity getEmployees() {
        return new ResponseEntity(employeeService.getEmployees(), HttpStatus.OK);
    }
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> findByDepartment(@PathVariable Long departmentId) {
        List<Employee> employees = employeeService.findByDepartment(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Employee>> findByOrganization(@PathVariable Long organizationId) {
        List<Employee> employees = employeeService.findByOrganization(organizationId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    
}
