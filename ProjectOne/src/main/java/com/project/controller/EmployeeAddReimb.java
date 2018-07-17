package com.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dao.DAOServiceImpl;

public class EmployeeAddReimb {

	public static void create(HttpServletRequest request,HttpServletResponse response) {
			
			DAOServiceImpl dao = new DAOServiceImpl();
			
			int userId = Integer.parseInt(request.getParameter("userId"));
			int type = Integer.parseInt(request.getParameter("type"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			String description = request.getParameter("description");
			int status = 0;
			
			int result = dao.createReimbRequest(amount, description, userId, status, type);
			if(result == 0) {
				String resultString[] = {"0"};
				response.setContentType("application/json");
				try {
					response.getWriter().write(new ObjectMapper().writeValueAsString(resultString));
				} catch (JsonProcessingException e) {
					System.out.println("Dang, something went wrong with the JSON thing when creating reimbursement!");
				} catch (IOException e) {
					System.out.println("Dang, something went wrong with the IO thing when creating reimbursement!");
				}
			} else {
				String resultString[] = {"1"};
				response.setContentType("application/json");
				try {
					response.getWriter().write(new ObjectMapper().writeValueAsString(resultString));
				} catch (JsonProcessingException e) {
					System.out.println("Dang, something went wrong with the JSON thing when creating reimbursement!");
				} catch (IOException e) {
					System.out.println("Dang, something went wrong with the IO thing when creating reimbursement!");
				}
			}
	}
}
