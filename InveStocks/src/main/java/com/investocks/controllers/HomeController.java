package com.investocks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.investocks.entities.User;
import com.investocks.forms.UserForm;
import com.investocks.helper.Message;
import com.investocks.helper.MessageType;
import com.investocks.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomeController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String initialController() {
        return "home";
    }
    @GetMapping("/home")
    public String homeController() {
        return "home";
    }
    
    @GetMapping("/login")
    public String loginController() {
        return "login";
    }
    @GetMapping("/register")
    public String registerController(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }
    
    @PostMapping("/do-register")
    public String submitRegistration(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session) {

        if(rBindingResult.hasErrors()){
            return "register";
        }

        User user = new User();
        user.setUserName(userForm.getUserName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setCountry(userForm.getCountry());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAccountBalance(1000000);
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());   

        userServices.saveUser(user);
        Message message = Message.builder().content("Registration Succesfull!").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/register";
    }
    
}
