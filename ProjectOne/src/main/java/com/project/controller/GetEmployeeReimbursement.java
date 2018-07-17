package com.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.classes.Reimbursement;
import com.project.dao.DAOServiceImpl;

public class GetEmployeeReimbursement {

	public static void get(HttpServletRequest request,HttpServletResponse response) {
		DAOServiceImpl dao = new DAOServiceImpl();
		
		int userId = Integer.parseInt(request.getParameter("userId"));
//		System.out.println("Userid: " + userId);
		List<Reimbursement> reimbList = dao.selectAllReimbByEmployeeId(userId);
//		for(int i = 0; i < reimbList.size(); i++) {
//			System.out.println(reimbList.get(i).toString());
//		}
		response.setContentType("application/json");
		try {
			response.getWriter().write(new ObjectMapper().writeValueAsString(reimbList));
		} catch (IOException e) {
			System.out.println("Oh Dang, something went wrong when grabbing the data!");
		}
	}
}
