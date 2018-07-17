package com.project.dao;

import java.sql.Blob;
import java.util.List;

import com.project.classes.Reimbursement;
import com.project.classes.User;

public interface DAOService {
	
	//CREATE
	//create either employee or finance manager account based on the parameter "role"
	public int createAccount(String username,String password,String fname,String lname,String email, int role);
	//create reimbursement but without the blob
	public int createReimbRequest(double amount,String description,int author,int status,int type);//without blob
	//create reimbursement but with the blob
	public int createReimbRequest(double amount,String description,Blob receipt,int author,int status,int type);//with blob
	
	//READ
//--------------------All Reimbursements------------------------------------------------------------------
	//select all reimbursements
	public List<Reimbursement> selectAllReimb();
	//select all reimbursements by their status
	public List<Reimbursement> selectAllReimbByStatus(int status);
	//select all reimbursements by user and role
	public List<Reimbursement> selectAllReimbByUser(int user,int role);
	//select all reimbursements by the employee's id
	public List<Reimbursement> selectAllReimbByEmployeeId(int user);
	//select all reimbursements by their author and status
	public List<Reimbursement> selectAllReimbByAuthorStatus(int author,int status);
	//select all reimbursements by their author and type
	public List<Reimbursement> selectReimbByAuthorType(int author, int type);
	//select all reimbursements by their type
	public List<Reimbursement> selectReimByType(int type);
	//select reimbursements by id
	public Reimbursement selectReimbById(int id);
//---------------------------------------------------------------------------------------------

//----------------------------------Users------------------------------------------------------
	//select a user by their id
	public User getUserByID(int id);
	//select a user by their username
	public User getUserByUsername(String username);
	//
	public User getUserByEmail(String email);
	//select all users by their roles
	public List<User> getAllUsersByRoles(int role);

	
	//UPDATE
	//update reimbursement's resolved time (when it was approved/denied)
	public int updateReimbResolvedTime(int id);
	//update reimbursement's resolver (the one who approved/denied the reimbursement)
	public int updatedReimbResolver(int reimbId,int resolver);
	//update the reimbursement's status (whether it is still pending, accepted, or denied)
	public int updatedReimbStatus(int id,int status);
}
