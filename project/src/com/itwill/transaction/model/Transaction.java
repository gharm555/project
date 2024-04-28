package com.itwill.transaction.model;

import java.util.Date;

public class Transaction {
    private int id;
    private String type; // '수입', '지출'
    private double amount;
    private int categoryId;
    private Date date;
    private String description;

    // 생성자
    public Transaction(int id, String type, double amount, int categoryId, Date date, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
        this.description = description;
    }

    // getter 및 setter 메소드
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

   
    // 데이터베이스 연동 로직 구현 (예: 거래 추가, 수정, 삭제 등)
}

