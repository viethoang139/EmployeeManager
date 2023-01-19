package com.leviethoang.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    public Page<Employee> listAll(int pageNum){
        Pageable pageable = PageRequest.of(pageNum - 1 , 5);
        return repo.findAll(pageable);
    }

    public Employee register(Employee employee){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncoder = encoder.encode(employee.getPassword());
        employee.setPassword(passwordEncoder);
        repo.save(employee);
        return employee;
    }

    public Employee edit(Employee employee){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncoder = encoder.encode(employee.getPassword());
        employee.setPassword(passwordEncoder);
        repo.save(employee);
        return employee;
    }

    public Employee getById(Integer id) throws EmployeeNotFoundException {
        Optional<Employee> byId = repo.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new EmployeeNotFoundException("Not found employee with ID: " + id);
    }

    public void deleteById(Integer id) throws EmployeeNotFoundException {
        Long count = repo.countById(id);
        if(count == 0 || count == null){
            throw new EmployeeNotFoundException("Not found employee with ID: " + id);
        }
        repo.deleteById(id);
    }

    public List<Employee> search(String keyword){
        return repo.search(keyword);
    }

    public void updateResetPasswordToken(String token , String email) throws EmployeeNotFoundException {
        Employee employee = repo.findByEmail(email);
        if(employee != null){
            employee.setResetPasswordToken(token);
            repo.save(employee);
        }else{
            throw new EmployeeNotFoundException("Not found employee with email: " + email);

        }
    }

    public Employee getByResetPasswordToken(String token){
        return repo.findByResetPasswordToken(token);
    }

    public void updatePassword(Employee employee , String newPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoderPassword = encoder.encode(newPassword);
        employee.setPassword(encoderPassword);
        employee.setResetPasswordToken(null);
        repo.save(employee);
    }

}
