package com.codegym.controller;

import com.codegym.model.Boss;
import com.codegym.service.boss.IBossService;
import com.codegym.service.employee.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/bossPage")
public class BossController {
    @Autowired
    IBossService bossService;
    @Autowired
    IEmployeeService employeeService;

    @GetMapping("")
    public String showBossList(Model model){
        model.addAttribute("bosses",bossService.findAll());
        return "boss/list";
    }

    @GetMapping("create")
    public ModelAndView showCreateForm(){
        ModelAndView mav = new ModelAndView("boss/create");
        mav.addObject("boss",new Boss());
        return mav;
    }
    @PostMapping("create")
    public String createBoss(@Validated @ModelAttribute("boss")Boss boss,BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "/boss/create";
        }
        bossService.save(boss);
        model.addAttribute("message","Create boss successful!");
        return showBossList(model);
    }


    @GetMapping("{id}/edit")
    public ModelAndView showEditForm(@PathVariable("id")Optional<Boss> boss){
        ModelAndView mav = new ModelAndView("boss/edit");
        mav.addObject("boss",boss.get());
        return mav;
    }
    @PostMapping("{id}/edit")
    public String editBoss(@ModelAttribute("boss") Boss boss, RedirectAttributes redirect){
        bossService.save(boss);
        redirect.addFlashAttribute("message","Edit boss successful!");
        return "redirect:/bossPage";
    }

    @GetMapping("{id}/delete")
    public ModelAndView showDeleteForm(@PathVariable("id")Optional<Boss> boss){
        ModelAndView mav = new ModelAndView("boss/delete");
        mav.addObject("boss",boss.get());
        return mav;
    }
    @PostMapping("{id}/delete")
    public String deleteBoss(@ModelAttribute("boss") Boss boss,RedirectAttributes redirect){
        bossService.remove(boss.getId());
        redirect.addFlashAttribute("message","Delete boss successful!");
        return "redirect:/bossPage";
    }

    @GetMapping("{id}/detail")
    public ModelAndView showBossDetail(@PathVariable("id") Optional<Boss> boss){
        ModelAndView mav = new ModelAndView("boss/detail");

        mav.addObject("employees",employeeService.findAllByBoss(boss.get()));
        return mav;
    }


}
