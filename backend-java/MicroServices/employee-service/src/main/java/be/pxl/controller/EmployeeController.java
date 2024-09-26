package be.pxl.controller;

import be.pxl.domain.Employee;
import be.pxl.domain.dto.EmployeeRequest;
import be.pxl.domain.dto.EmployeeResponse;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeRequest employee) {
        employeeService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
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
    public ResponseEntity findByDepartment(@PathVariable Long departmentId) {
        List<EmployeeResponse> employees = employeeService.findByDepartment(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity findByOrganization(@PathVariable Long organizationId) {
        List<EmployeeResponse> employees = employeeService.findByOrganization(organizationId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    
}
