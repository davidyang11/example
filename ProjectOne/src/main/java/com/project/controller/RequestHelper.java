package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestHelper {

	public static void process(HttpServletRequest request,HttpServletResponse response) {
//		System.out.println(request.getRequestURL());
		switch(request.getRequestURI()) {
		case "/ProjectOne/html/check.ex":
//			System.out.println("check.ex");
			LoginController.login(request,response);
			break;
		case "/ProjectOne/html/create.ex":
//			System.out.println("Create.ex");
			CreateController.create(request,response);
			break;
		case "/ProjectOne/html/employeeReimbAll.ex":
//			System.out.println("employeeReimbAll.ex");
			GetEmployeeReimbursement.get(request,response);
			break;
		case "/ProjectOne/html/employeeAddReimb.ex":
//			System.out.println("employeeAddReimb.ex");
			EmployeeAddReimb.create(request,response);
			break;
		case "/ProjectOne/html/managerReimbAll.ex":
//			System.out.println("managerReimbAll.ex");
			GetAllReimbursement.get(request,response);
			break;
		case "/ProjectOne/html/decide.ex":
//			System.out.println("approve.ex");
			ApproveOrDenyController.decide(request,response);
			break;
		case "/ProjectOne/html/filterStatus.ex":
//			System.out.println("filterStatus.ex");
			FilterStatusController.findAll(request,response);
			break;
		case "/ProjectOne/html/filterType.ex":
			FilterTypeController.findAll(request,response);
			break;
		case "/ProjectOne/html/eFilterStatus.ex":
			EmployeeFilterStatusController.findAll(request,response);
			break;
		case "/ProjectOne/html/eFilterType.ex":
			EmployeeFilterTypeController.findAll(request,response);
			break;
		default:
			break;
		}
	}
	
}
