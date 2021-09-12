package com.younes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.younes.entity.User;
import com.younes.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/list-users")
	public String allUsers(Model model) {
		model.addAttribute("title", "List-Users");
		List<User> listuser=userService.findAllUsers();
		model.addAttribute("listuser", listuser);
		
		return "list_users";
	}
	@GetMapping("/success")
	public String success(Model model) {
		model.addAttribute("title", "success");
		
		return "success";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("title", "Registration");
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/register-user")
	public String registerUser(@ModelAttribute User user, Model model) {
		model.addAttribute("title", "success");
		userService.saveUser(user);
		return "redirect:/user/success";
	}
	@GetMapping("/update/{id}")
	public String editUser(@PathVariable("id")Long id,Model model) {
		model.addAttribute("title", "Edit-user");
		User user=userService.findUserById(id);
        
		model.addAttribute("user", user);
		return "edit_form";
	}
	@PostMapping("/edit")
	public String editUser(@ModelAttribute User user) {
		
		userService.saveUser(user);
		
		return "redirect:/user/list-users";
	}
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id")Long id) {

		userService.deleteUser(id);
        
		return "redirect:/user/list-users";
	}
}
