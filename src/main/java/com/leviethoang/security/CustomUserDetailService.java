package com.leviethoang.security;

import com.leviethoang.employee.Employee;
import com.leviethoang.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private EmployeeRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = repo.getEmployeeByEmail(email);
        if(employee == null){
            throw new UsernameNotFoundException("Could not found email: " + email);
        }
        return new CustomUserDetail(employee);
    }
}
