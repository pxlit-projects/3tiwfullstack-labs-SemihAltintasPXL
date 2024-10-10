package be.pxl.service.impl;

import be.pxl.client.NotificationClient;
import be.pxl.domain.Employee;
import be.pxl.domain.dto.EmployeeRequest;
import be.pxl.domain.dto.EmployeeResponse;
import be.pxl.domain.dto.NotificationRequest;
import be.pxl.repository.EmployeeRepository;
import be.pxl.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;

    @Override
    public List<EmployeeResponse> getEmployees() {
       return employeeRepository.findAll().stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::mapToEmployeeResponse).orElseThrow();
    }

    @Override
    public List<EmployeeResponse> findByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findByOrganization(Long organizationId) {
        return employeeRepository.findByOrganizationId(organizationId).stream().map(this::mapToEmployeeResponse).toList();
    }
    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .build();
    }
    @Override
    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .age(employeeRequest.getAge())
                .position(employeeRequest.getPosition())
                .build();
        employeeRepository.save(employee);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Employee " + employee.getName() + " created")
                .sender("Kerim")
                .build();
        notificationClient.sendNotification(notificationRequest);
    }
}
