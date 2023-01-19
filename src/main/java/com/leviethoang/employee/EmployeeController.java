package com.leviethoang.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/employees")
    public String showListEmployees(Model model){
        return showPage(model , 1);
    }

    @GetMapping("/employees/{pageNum}")
    public String showPage(Model model , @PathVariable("pageNum")int pageNum){
        Page<Employee> page = service.listAll(pageNum);
        Long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Employee> employees = page.getContent();
        model.addAttribute("employees" , employees);
        model.addAttribute("totalElements" , totalElements);
        model.addAttribute("totalPages" , totalPages);
        model.addAttribute("pageNum" , pageNum);
        return "employee";
    }

    @GetMapping("/register")
    public String addNewEmployee(Model model){
        model.addAttribute("employees" , new Employee());
        return "register_form";
    }

    @PostMapping("/register")
    public String registerEmployee(Employee employee, RedirectAttributes ra, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        employee.setPhotos(fileName);
        Employee employees =  service.register(employee);

        String uploadDir = "employee-photos/" + employee.getId();

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream =  multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream , filePath , StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioe){
            throw  new IOException("Could not found file " + fileName , ioe);
        }

        ra.addFlashAttribute("registerMessage" , "Register successfully. Please sign in");
        return "redirect:/login";
    }

    @PostMapping("/edit")
    public String editEmployee(Employee employee, RedirectAttributes ra, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        employee.setPhotos(fileName);
        Employee employees =  service.edit(employee);

        String uploadDir = "employee-photos/" + employee.getId();

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream =  multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream , filePath , StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioe){
            throw  new IOException("Could not found file " + fileName , ioe);
        }

        ra.addFlashAttribute("editMessage" , "Employee has been edit successfully!!!");
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String editEmployee(@PathVariable("id") Integer id , Model model )  {
         try {
            Employee employees = service.getById(id);
            model.addAttribute("employees" , employees);
            return "edit_form";
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id , RedirectAttributes ra){
        try {
            service.deleteById(id);
            ra.addFlashAttribute("deleteMessage" , "Employee has been delete successfully!!!");
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/employees";
    }

    @GetMapping("/employees/search")
    public String searchResult(@Param("keyword")String keyword , Model model){
        List<Employee> employees = service.search(keyword);
        model.addAttribute("employees" , employees);
        model.addAttribute("keyword" , keyword);
        return "search_result";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("employees" , new Employee());
        return "login";
    }



}
