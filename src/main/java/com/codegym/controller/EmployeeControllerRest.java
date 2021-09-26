package com.codegym.controller;


import com.codegym.dto.ResponseMessage;
import com.codegym.model.Employee;
import com.codegym.service.employee.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@PropertySource("classpath:imageFile.properties")
@PropertySource("classpath:audioFile.properties")
public class EmployeeControllerRest {

    @Value("${file-image}")
    private String imageFile;
    @Value("${file-audio}")
    private String audioFile;
 @Autowired
    IEmployeeService employeeService;

 @GetMapping("/listEmployee")
    public ResponseEntity<?> listEmployee(){
     return new ResponseEntity<>( employeeService.findAll(), HttpStatus.OK);
 }
//    @PostMapping("/createEmployee")
//    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
//        employeeService.save(employee);
//        return new ResponseEntity<>(new ResponseMessage("create success!"), HttpStatus.OK);

// }

    @PostMapping("/createEmployee")
    public ResponseEntity<?> createEmployee(@RequestParam("imageFile") MultipartFile file, Employee employee){


        String imgStringName = file.getOriginalFilename();
        String audioStringName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(),new File(imageFile+imgStringName));
            FileCopyUtils.copy(file.getBytes(),new File(audioFile+ audioStringName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        employee.setImg(imageFile);
        employee.setImg(audioFile);
        employeeService.save(employee);

        return new ResponseEntity<>(new ResponseMessage("create success!"), HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> findByEmployee(@PathVariable String name){
        return new ResponseEntity<>(employeeService.findAllByProducerContaining(name),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detailEmployee(@PathVariable Long id){
        Optional<Employee> employee = employeeService.findById(id);
        if(!employee.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        Optional<Employee> employee = employeeService.findById(id);
        if(!employee.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       employeeService.remove(employee.get().getId());
        return new ResponseEntity<>(new ResponseMessage("Delete Success!"), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSmartPhone(@PathVariable Long id, @RequestBody Employee employee){
        Optional<Employee> employee1 = employeeService.findById(id);
        if(!employee1.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employee.setId(id);
      employeeService.save(employee);
        return new ResponseEntity<>(new ResponseMessage("Update Success!"), HttpStatus.OK);
    }

}
