package be.pxl.service;

import be.pxl.domain.Organization;
import be.pxl.domain.dto.OrganizationResponse;

import java.util.List;

public interface IOrganizationService {
    OrganizationResponse getOrganizationById(Long id);

    OrganizationResponse getOrganizationByIdWithDepartments(Long id);

    OrganizationResponse getOrganizationByIdWithEmployees(Long id);

    OrganizationResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id);
}
