package com.leviethoang;

import com.leviethoang.employee.Employee;
import com.leviethoang.employee.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repo;
    @Test
    public void testAddNew(){
        Employee employee = new Employee();
        employee.setEmail("viethoangle@gmail.com");
        employee.setPassword("hoang123");
        employee.setFirstName("Le Viet");
        employee.setLastName("Hoang");
        employee.setAddress("Tuyen Quang");
        employee.setGender("Male");

        Employee savedEmployee =  repo.save(employee);


        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    @Test
    public void testUpdate(){
        Integer id = 1;
        Optional<Employee> byId = repo.findById(id);
        Employee employee = byId.get();
        employee.setPassword("hoang1309");

        repo.save(employee);

        Employee savedEmployee = repo.findById(id).get();

        Assertions.assertThat(savedEmployee.getPassword()).isEqualTo("hoang1309");

    }

    @Test
    public void testGetById(){
        Integer id = 2;
        Optional<Employee> byId = repo.findById(id);
        Assertions.assertThat(byId).isPresent();
        Employee employee = byId.get();
        System.out.println(employee.toString());
    }

    @Test
    public void testListAll(){
        List<Employee> employees = repo.findAll();
        Assertions.assertThat(employees).hasSizeGreaterThan(0);
        for(Employee employee : employees){
            System.out.println(employee.toString());
        }
    }

    @Test
    public void testDeleteById(){
        Integer id = 2;
        repo.deleteById(id);
        Optional<Employee> byId = repo.findById(id);
        Assertions.assertThat(byId).isNotPresent();
    }

}
