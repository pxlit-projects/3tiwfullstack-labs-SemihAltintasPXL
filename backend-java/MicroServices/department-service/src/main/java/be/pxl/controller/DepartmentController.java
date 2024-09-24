package be.pxl.controller;

import be.pxl.domain.dto.DepartmentRequest;
import be.pxl.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody DepartmentRequest departmentRequest) {
        departmentService.addDepartment(departmentRequest);
    }
    @GetMapping
    public ResponseEntity getDepartments() {
        return new ResponseEntity(departmentService.getAllEmployees(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity getDepartmentById(@PathVariable Long id) {
        return new ResponseEntity(departmentService.getDepartmentById(id), HttpStatus.OK);
    }
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity getDepartmentByOrganizationId(@PathVariable Long organizationId) {
        return new ResponseEntity(departmentService.getDepartmentsByOrganizationId(organizationId), HttpStatus.OK);
    }
    @GetMapping("/organization/{organizationId}/with-employees")
    public ResponseEntity getDepartmentByOrganizationIdWithEmployees(@PathVariable Long organizationId) {
        return new ResponseEntity(departmentService.getDepartmentsByOrganizationIdWithEmployees(organizationId), HttpStatus.OK);
    }

}
