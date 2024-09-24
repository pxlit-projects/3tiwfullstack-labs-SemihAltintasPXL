package be.pxl.service;

import be.pxl.domain.Department;
import be.pxl.domain.dto.DepartmentRequest;
import be.pxl.domain.dto.DepartmentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public  interface IDepartmentService {
    List<DepartmentResponse> getAllEmployees();

    void addDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getDepartmentsByOrganizationId(Long organizationId);

    List<DepartmentResponse> getDepartmentsByOrganizationIdWithEmployees(Long organizationId);
}
