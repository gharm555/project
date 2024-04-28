package com.itwill.transaction.model;

public class Category {
    private int categoryId;
    private String name;
    private String type; // '수입', '지출'

    // 생성자
    public Category(int categoryId, String name, String type) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
    }

    // getter 및 setter 메소드
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // 기타 메소드
    // 데이터베이스 연동 로직 구현 (예: 카테고리 추가, 수정, 삭제 등)
}

