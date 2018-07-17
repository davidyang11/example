package com.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.classes.User;
import com.project.dao.DAOServiceImpl;


public class LoginController {

	public static void login(HttpServletRequest request,HttpServletResponse response) {
		
		DAOServiceImpl dao = new DAOServiceImpl();
		
		String username = request.getParameter("inputUsername");
		String password = request.getParameter("inputPassword");
		
		User u = dao.getUserByUsername(username);
		
		if((username.equals(u.getUsername())) && (password.equals(u.getPassword()))) {
			try {
				
				String result[] = {"1",Integer.toString(u.getRole()),Integer.toString(u.getId()),u.getFname(),u.getLname()};
				response.setContentType("application/json");
				response.getWriter().write(new ObjectMapper().writeValueAsString(result));
			} catch (IOException e) {
				System.out.println("Damn, something went wrong when trying to write back as a JSON.");
			}
		} else {
			response.setContentType("application/json");
			try {
				String result[] = {"0"};
				response.getWriter().write(new ObjectMapper().writeValueAsString(result));
			} catch (IOException e) {
				System.out.println("Damn, something went wrong when trying to write back as a JSON.");
			};
		}
	}
	
}
