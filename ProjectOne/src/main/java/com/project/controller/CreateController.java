package com.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dao.DAOServiceImpl;

public class CreateController {
	
	public static void create(HttpServletRequest request,HttpServletResponse response) {
		
		DAOServiceImpl dao = new DAOServiceImpl();
		//need to implement my encryption service
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		int role = 0;//employee
/*		System.out.println("Username: "+username);
		System.out.println("Password: "+password);
		System.out.println("fname: "+fname);
		System.out.println("lname: "+lname);
		System.out.println("email: "+email);
*/		
		int status = dao.createAccount(username,password,fname,lname,email,role);
		
		if(status == 0) {//not successful
			String result[] = {"0"};
			response.setContentType("application/json");
			try {
				response.getWriter().write(new ObjectMapper().writeValueAsString(result));
			} catch (IOException e) {
				System.out.println("Oh Dang, something went wrong when creating the account!");
			}
		} else {//successful
			String result[] = {"1"};
			response.setContentType("application/json");
			try {
				response.getWriter().write(new ObjectMapper().writeValueAsString(result));
			} catch (IOException e) {
				System.out.println("Oh Dang, something went wrong when creating the account!");
			}			
		}
		
	}

}
