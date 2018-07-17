package com.project.classes;

import java.sql.Blob;

public class Reimbursement {
	private int id;
	private double amount;
	private String dateSubmitted;
	private String dateResolved;
	private String description;
	private Blob receipt;
	private int author;
	private String name;
	private int resolver;
	private int status;
	private int type;
	
	public Reimbursement() {
		//empty constructor for empty object
	}

	//constructor for when user wants to submit a reimbursement (without the Blob)
	public Reimbursement(double amount, String description, int author, int status, int type) {
		super();
		this.amount = amount;
		this.description = description;
		this.author = author;
		this.status = status;
		this.type = type;
	}
	
	//constructor for when user wants to submit a reimbursement (with the Blob)
	public Reimbursement(double amount, String description, Blob receipt, int author, int status, int type) {
		super();
		this.amount = amount;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.status = status;
		this.type = type;
	}
	
	//reimb_id, reimb_type_id, reimb_status_id, reimb_amount, user_first_name, user_last_name, reimb_submitted, reimb_description
	public Reimbursement(int id, int type, int status, double amount, String fname, String lname, String dateSubmitted, String description) {
		this.id = id;
		this.type = type;
		this.status = status;
		this.amount = amount;
		this.name = fname + " " + lname;
		this.dateSubmitted = dateSubmitted;
		this.description = description;
		
	}
	
	//constructor for getting data from database
	public Reimbursement(int id, double amount, String dateSubmitted, String dateResolved, String description,
			Blob receipt, int author, int resolver, int status, int type) {
		super();
		this.id = id;
		this.amount = amount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(String dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(String dateResolved) {
		this.dateResolved = dateResolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Blob getReceipt() {
		return receipt;
	}

	public void setReceipt(Blob receipt) {
		this.receipt = receipt;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String fname, String lname) {
		this.name = fname + " " + lname;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", dateSubmitted=" + dateSubmitted + ", dateResolved="
				+ dateResolved + ", description=" + description + ", receipt=" + receipt + ", author=" + author
				+ ", name=" + name + ", resolver=" + resolver + ", status=" + status + ", type=" + type + "]";
	}

}
