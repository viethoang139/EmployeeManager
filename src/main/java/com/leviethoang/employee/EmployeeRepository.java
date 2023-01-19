package com.leviethoang.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface EmployeeRepository extends JpaRepository<Employee , Integer> {
    public Long countById(Integer id);

    @Query(value = "select * from Employee where " + "match(email , first_name, last_name , address , gender)"
            + "against(?1)" , nativeQuery = true)
    public List<Employee> search(String keyword);

    @Query("select e from  Employee e where e.email = ?1")
    public Employee getEmployeeByEmail(String email);


    @Query("select e from Employee e where e.email = ?1")
    public Employee findByEmail(String email);

    public Employee findByResetPasswordToken(String token);


}
