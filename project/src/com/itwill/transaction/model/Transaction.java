package com.itwill.transaction.model;

import java.util.Date;

public class Transaction {
	public static final class Entity {
		public static final String TBL_Transaction = "MONEYMANAGER";
		public static final String COL_ID = "ENTRYID";
		public static final String COL_PAYMENT = "PAYMENTMETHOD";
		public static final String COL_INCOME = "INCOMESOURCE";
		public static final String COL_CATEGORY_NAME = "CATEGORYNAME";
		public static final String COL_EXPENSE_DATE = "EXPENSEDATE";
		public static final String COL_INCOME_DATE = "INCOMEDATE";
		public static final String COL_NOTES = "NOTES";

	}

	private int id;
	private String payment;
	private String income;
	private String catName;
	private Date exDate;
	private Date inDate;
	private String notes;

	public Transaction(int id, String payment, String income, String catName, Date exDate, Date inDate, String notes) {
		super();
		this.id = id;
		this.payment = payment;
		this.income = income;
		this.catName = catName;
		this.exDate = exDate;
		this.inDate = inDate;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Date getExDate() {
		return exDate;
	}

	public void setExDate(Date exDate) {
		this.exDate = exDate;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", payment=" + payment + ", income=" + income + ", catName=" + catName
				+ ", exDate=" + exDate + ", inDate=" + inDate + ", notes=" + notes + "]";
	}

}
