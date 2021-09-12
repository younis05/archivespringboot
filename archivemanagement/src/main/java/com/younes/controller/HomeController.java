package com.younes.controller;



import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.younes.entity.User;
import com.younes.service.impl.UserServiceImpl;

@Controller
public class HomeController {

	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/")
	public String viewHome(Model model) {
		model.addAttribute("title", "Home");
		
		return "index";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session,Model model) {
		model.addAttribute("logout","logout");
		session.removeAttribute("firstname");
		return "redirect:/";
	}
	@GetMapping("/login")
	public String login(@ModelAttribute User user,Model model) {
		model.addAttribute("title", "Login");
		model.addAttribute("user",user);
		
		return "login";
	}
	
	@PostMapping("/login/user")
	public String userlogin(@ModelAttribute User user,BindingResult br,
			HttpSession session,Model model) {
		model.addAttribute("title", "Login");
		User emailexists=userService.getUserByEmail(user.getEmail());
		
		
		try {
			if(emailexists==null) {
				 model.addAttribute("error", "error");
		         return "login";
			}else{
				String email=emailexists.getEmail();
				String pass=emailexists.getPassword();
				String firstname=emailexists.getFirstName();
				System.out.println("email:"+firstname);
				
				if(email.equals(user.getEmail()) && pass.equals(user.getPassword())) {
					session.setAttribute("firstname", firstname);
					return "redirect:/";
				}else {
					 model.addAttribute("error", "error");
					return "login";
				}
					 
			}
			
			
		}catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "error");
            return "login";
        }
		
		
		  
	}
}
