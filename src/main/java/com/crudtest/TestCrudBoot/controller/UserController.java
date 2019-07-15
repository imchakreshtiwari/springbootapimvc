package com.crudtest.TestCrudBoot.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.crudtest.TestCrudBoot.model.User;
import com.crudtest.TestCrudBoot.repository.UserRepository;

@Controller
public class UserController {

	UserRepository userRepository;
//	User user=new User();

	public UserController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@GetMapping("/signup")
	public String showSignupForm(User user) {
		System.out.println("inside signup");
		return "add-user";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println("inside if if errors");
			return "add-user";
		} else {
			userRepository.save(user);
			System.out.println("inside else");
			Iterable<User> all = userRepository.findAll();
			model.addAttribute("users", all);
			return "index";
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {

		try {
			Optional<User> user = userRepository.findById(id);

			model.addAttribute("user", user.get());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return "update-user";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid User user, Model model, BindingResult result) {
		if (result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}
		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());
		return "index";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {

		try {
			Optional<User> user = userRepository.findById(id);
			userRepository.delete(user.get());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		model.addAttribute("users", userRepository.findAll());
		return "index";
	}

}
