package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.dto.UserDTO;
import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.repository.UserRepository;
import com.example.naitei19javaecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @GetMapping
    public String doRegiter(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "user/static-pages/register";
    }

    @PostMapping
    public String processRegister(@Valid @ModelAttribute("user") UserDTO userDto, Model model, BindingResult bindingResult) throws InterruptedException{
        if(userRepository.findByUsername(userDto.getUsername()) != null){
            bindingResult.rejectValue("username", null, "Username is already taken");
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("user", userDto);
            return  "user/static-pages/register";
        }

        userService.saveUser(userDto);
        Thread.sleep(3000);
        return "redirect:/login";
    }
}
