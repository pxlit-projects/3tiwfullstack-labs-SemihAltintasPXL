package be.pxl;

import be.pxl.domain.Department;
import be.pxl.domain.Employee;
import be.pxl.repository.DepartmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
// Configure the context of the tests to use the DepartmentServiceApplication because we have multiple Spring Boot applications
@ContextConfiguration(classes = DepartmentServiceApplication.class)
public class DepartmentTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Container
    private static MySQLContainer sqlContainer =
            new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }
    @AfterEach
    public void clearDatabase() {
        departmentRepository.deleteAll();
    }
    @Test
    public void testCreateDepartment() throws Exception {

        Department department = Department.builder()
                .organizationId(1234567L)
                .name("HR")
                .position("student")
                .build();

        String departmentString = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentString))
                .andExpect(status().isCreated());

        assertEquals(1, departmentRepository.findAll().size());
    }

    @Test
    public void testGetDepartmentById() throws Exception {

        Department department = Department.builder()
                .organizationId(1234567L)
                .name("HR")
                .position("student")
                .build();

        Department savedDepartment = departmentRepository.save(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + savedDepartment.getId()))
                .andExpect(status().isOk());

        Optional<Department> retrievedDepartment = departmentRepository.findById(savedDepartment.getId());

        assertTrue(retrievedDepartment.isPresent());
        assertEquals(department.getId(), retrievedDepartment.get().getId());
    }

    @Test
    public void testGetEmployees() throws Exception {
        Department department1 = Department.builder()
                .organizationId(1234567L)
                .name("HR")
                .position("student")
                .build();
        Department savedDepartment1 = departmentRepository.save(department1);

        Department department2 = Department.builder()
                .organizationId(1234567L)
                .name("Finance")
                .position("student")
                .build();
        Department savedDepartment2 = departmentRepository.save(department2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department"))
                .andExpect(status().isOk());

        List<Department> retrievedDepartments = departmentRepository.findAll();

        assertEquals(2, departmentRepository.findAll().size());
        assertEquals(savedDepartment1.getId(), retrievedDepartments.get(0).getId());
        assertEquals(savedDepartment2.getId(), retrievedDepartments.get(1).getId());
    }

    @Test
    public void findDepartmentByOrganization() throws Exception{

        Department department = Department.builder()
                .organizationId(1234567L)
                .name("HR")
                .position("student")
                .build();

        Department savedDepartment = departmentRepository.save(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + savedDepartment.getOrganizationId()))
                .andExpect(status().isOk());

        Optional<Department> retrievedDepartment = departmentRepository.findById(savedDepartment.getId());

        assertTrue(retrievedDepartment.isPresent());
        assertEquals(department.getId(), retrievedDepartment.get().getId());
    }

    @Test
    public void findDepartmentByOrganizationWithEmployees() throws Exception{

        List<Employee> employees = new ArrayList<>();

        Employee employee1 = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .build();

        Employee employee2 = Employee.builder()
                .age(28)
                .name("Alice")
                .position("developer")
                .build();

        employees.add(employee1);
        employees.add(employee2);

        Department department = Department.builder()
                .organizationId(1234567L)
                .name("HR")
                .position("student")
                .employees(employees)
                .build();

        Department savedDepartment = departmentRepository.save(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + savedDepartment.getOrganizationId() + "/with-employees"))
                .andExpect(status().isOk());

        Optional<Department> retrievedDepartment = departmentRepository.findById(savedDepartment.getId());

        assertTrue(retrievedDepartment.isPresent());
        assertEquals(department.getId(), retrievedDepartment.get().getId());
        assertEquals(2, department.getEmployees().size());
    }
}
