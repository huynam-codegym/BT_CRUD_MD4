package com.codegym.controller;

import com.codegym.model.Boss;
import com.codegym.model.Employee;
import com.codegym.service.boss.IBossService;
import com.codegym.service.employee.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("employeePage")
@PropertySource("classpath:imageFile.properties")
@PropertySource("classpath:audioFile.properties")
public class EmployeeController {
    @Value("${file-image}")
    private String imageFile;
    @Value("${file-audio}")
    private String audioFile;

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IBossService bossService;

    @ModelAttribute("bosses")
    public Iterable<Boss> bosses(){
        return bossService.findAll();
    }

    @GetMapping("")
    public String showListEmployee(@RequestParam("search")Optional<String> search
            , @PageableDefault(page = 0,size = 3,sort = "id",direction = Sort.Direction.DESC) Pageable pageable
            ,Model model){
        Page<Employee> employees;
        if(search.isPresent()){
            employees = employeeService.findAllByNameContaining(search.get(),pageable);
            model.addAttribute("back","back to list");
        }else {
            employees=employeeService.findAll(pageable);
        }
        model.addAttribute("employees",employees);
        return "employee/list";
    }

    @GetMapping("create")
    public ModelAndView showCreateForm(){
        ModelAndView mav = new ModelAndView("employee/create");
        mav.addObject("employee",new Employee());
        return mav;
    }
    @PostMapping("create")
    public String createEmployee(@Validated @ModelAttribute("employee")Employee employee
            , BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "employee/create";
        }
        MultipartFile imgMultipartFile = employee.getImgMultipartFile();
        MultipartFile audioMultipartFile = employee.getAudioMultipartFile();

        String imgStringName = imgMultipartFile.getOriginalFilename();
        String audioStringName = audioMultipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(imgMultipartFile.getBytes(),new File(imageFile+imgStringName));
            FileCopyUtils.copy(audioMultipartFile.getBytes(),new File(audioFile+audioStringName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        employee.setImg(imgStringName);
        employee.setAudio(audioStringName);
        employeeService.save(employee);
        model.addAttribute("message","Add new employee successful!");
        Optional<String> opString = Optional.empty();
        return showListEmployee(opString, PageRequest.of(0,2,Sort.by("id").descending()),model);
    }

    @GetMapping("{id}/edit")
    public ModelAndView showEditEmployeeForm(@PathVariable("id")Optional<Employee> employee){
        ModelAndView mav = new ModelAndView("employee/edit");
        mav.addObject("employee",employee.get());
        return mav;
    }
    @PostMapping("{id}/edit")
    public String editEmployee(@ModelAttribute Employee employee,RedirectAttributes redirect){

        MultipartFile imgMultipartFile = employee.getImgMultipartFile();
        MultipartFile audioMultipartFile = employee.getAudioMultipartFile();

        String imgStringName = imgMultipartFile.getOriginalFilename();
        String audioStringName = audioMultipartFile.getOriginalFilename();

        try {
            FileCopyUtils.copy(imgMultipartFile.getBytes(),new File(imageFile+imgStringName));
            FileCopyUtils.copy(audioMultipartFile.getBytes(),new File(audioFile+audioStringName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        employee.setImg(imgStringName);
        employee.setAudio(audioStringName);
        employeeService.save(employee);
        redirect.addFlashAttribute("message","Edit employee successful!");
        return "redirect:/employeePage";
    }

    @GetMapping("{id}/delete")
    public ModelAndView showDeleteEmployeeForm(@PathVariable("id")Optional<Employee> employee){
        ModelAndView mav = new ModelAndView("employee/delete");
        mav.addObject("employee",employee.get());
        return mav;
    }
    @PostMapping("{id}/delete")
    public String deleteEmployee(@ModelAttribute Employee employee,RedirectAttributes redirect){
        employeeService.remove(employee.getId());
        redirect.addFlashAttribute("message","Delete employee successful!");
        return "redirect:/employeePage";
    }

    @GetMapping("{id}/detail")
    public ModelAndView showDetailEmployee(@PathVariable("id")Optional<Employee> employee){
        ModelAndView mav = new ModelAndView("employee/detail");
        mav.addObject("employee",employee.get());
        return mav;
    }

}
