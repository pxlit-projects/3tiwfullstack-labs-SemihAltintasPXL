package be.pxl.service;

import be.pxl.domain.Employee;
import be.pxl.domain.dto.EmployeeRequest;
import be.pxl.domain.dto.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    List<EmployeeResponse> getEmployees();
    void addEmployee(EmployeeRequest employee);
    EmployeeResponse getEmployeeById(Long id);
    List<EmployeeResponse> findByDepartment(Long departmentId);
    List<EmployeeResponse> findByOrganization(Long organizationId);
}
