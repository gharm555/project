package com.itwill.transaction.model;

import java.util.Date;

public class Transaction {
	public static final class Entity {
		public static final String TBL_Transaction = "MONEYMANAGER";
		public static final String COL_ID = "ENTRYID";
		public static final String COL_TYPE = "TRANSACTIONTYPE";
		public static final String COL_CATEGORY = "CATEGORY";
		public static final String COL_TRANSACTION_DATE = "TRANSACTIONDATE";
		public static final String COL_AMOUNT = "AMOUNT";
		public static final String COL_NOTES = "NOTES";

	}

	private int id;
	private String type;
	private String category;
	private Date date;
	private double amount;
	private String notes;

	public Transaction(int id, String type, String category, java.util.Date date, String notes) {
		super();
		this.id = id;
		this.type = type;
		this.category = category;
		this.date = date;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", type=" + type + ", category=" + category + ", date=" + date	 + ", amount="
				+ amount + ", notes=" + notes + "]";
	}

}
