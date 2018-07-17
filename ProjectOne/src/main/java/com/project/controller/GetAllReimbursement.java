package com.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.classes.Reimbursement;
import com.project.dao.DAOServiceImpl;

public class GetAllReimbursement {

	public static void get(HttpServletRequest request,HttpServletResponse response) {
		DAOServiceImpl dao = new DAOServiceImpl();
		
		List<Reimbursement> reimbList = dao.selectAllReimb();
//		for(int i = 0; i < reimbList.size(); i++) {
//			System.out.println(reimbList.get(i));
//		}
		
		response.setContentType("application/json");
		try {
			response.getWriter().write(new ObjectMapper().writeValueAsString(reimbList));
		} catch (IOException e) {
			System.out.println("Oh Dang, something went wrong when grabbing all the reimbursements!");
		}

	}
}
