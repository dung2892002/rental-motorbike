package com.example.motorbike.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/userImages";
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@GetMapping("/changeInfor")
	public String getChangeInforPage(Model model, @SessionAttribute(required = false) User user) {
		if (user != null) {
			model.addAttribute("user", user);
			return "changeInforForm";
		} else {
			return "loginForm";
		}
	}
	
	@PutMapping("/save_user")
	public String updateUser(@ModelAttribute("user") User user1,
	                         @RequestParam("userImage") MultipartFile fileUserName,
	                         @RequestParam("imgName") String imgName,
	                         @SessionAttribute(required = false) User user,
	                         HttpSession session) throws IOException {
		
	    if (user == null) {
	        user = (User) session.getAttribute("user");
	    }

	    user.setPeople(user1.getPeople());

	    String imageUUID;

	    File uploadPath = new File(uploadDir);
	    if (!uploadPath.exists()) {
	        uploadPath.mkdirs();
	    }

	    if (!fileUserName.isEmpty()) {
	        imageUUID = fileUserName.getOriginalFilename();
	        Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
	        Files.write(fileNameAndPath, fileUserName.getBytes());
	    } else {
	        imageUUID = imgName;
	    }

	    user.setImageName(imageUUID);
	    userServiceImpl.updateUser(user);

	    session.setAttribute("user", user);

	    return "redirect:/customer";
	}

}
