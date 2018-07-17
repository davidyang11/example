package com.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.classes.Reimbursement;
import com.project.dao.DAOServiceImpl;

public class FilterTypeController {

	public static void findAll(HttpServletRequest request, HttpServletResponse response) {
		
		DAOServiceImpl dao = new DAOServiceImpl();
		
		int type = Integer.parseInt(request.getParameter("type"));
//		System.out.println(type);
		List<Reimbursement> reimbList = dao.selectReimByType(type);
		
		response.setContentType("application/json");
		try {
			response.getWriter().write(new ObjectMapper().writeValueAsString(reimbList));
		} catch (JsonProcessingException e) {
			System.out.println("Dang, something went wrong when grabbing all reimbursements based on a status! JSON!");
		} catch (IOException e) {
			System.out.println("Dang, something went wrong when grabbing all reimbursements based on a status! IO!");
		}
	}
}
