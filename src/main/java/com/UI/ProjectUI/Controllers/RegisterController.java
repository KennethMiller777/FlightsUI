package com.UI.ProjectUI.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.UI.ProjectUI.WebSecurityConfig;
import com.UI.ProjectUI.Models.User;

@RestController
@RequestMapping("/Register")
public class RegisterController {

    @GetMapping
    public ModelAndView index() { //return view
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Register");

        return mv;
    }

    @RequestMapping(value = "/CreateUser/{username}/{password}", method = RequestMethod.POST, produces="application/json")
    public boolean CreateUser(@PathVariable String username, @PathVariable String password) {
        String URLCreate = String.format("http://localhost:4000/API/Users/createUser/%s/%s", username, password);

        RestTemplate CreaterestTemplate = new RestTemplate();
        CreaterestTemplate.getForObject(URLCreate, User.class);

        WebSecurityConfig.userDetailsService();

        return true;
    }
}
