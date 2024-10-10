package be.pxl.client;

import be.pxl.domain.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {
    @GetMapping("api/employee/department/{departmentId}")
    List<Employee> findEmployeeByDepartment(@PathVariable Long departmentId);
}