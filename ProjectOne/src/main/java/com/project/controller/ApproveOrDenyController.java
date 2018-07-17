package com.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dao.DAOServiceImpl;

public class ApproveOrDenyController {

	public static void decide(HttpServletRequest request, HttpServletResponse response) {
	
		DAOServiceImpl dao = new DAOServiceImpl();
		
		int id = Integer.parseInt(request.getParameter("reimbId"));
		int status = Integer.parseInt(request.getParameter("status"));
		int resolver = Integer.parseInt(request.getParameter("resolverId"));
		
		int result = dao.updatedReimbStatus(id,status);
		int result2 = dao.updatedReimbResolver(id,resolver);
		int result3 = dao.updateReimbResolvedTime(id);

	}
}
