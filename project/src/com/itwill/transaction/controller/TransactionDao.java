package com.itwill.transaction.controller;

import java.util.Date;
import java.util.List;

import com.itwill.transaction.model.Transaction;

public interface TransactionDao {
    void insertTransaction(Transaction transaction);
    Transaction getTransactionById(int id);
    List<Transaction> getAllTransactions();
    void updateTransaction(Transaction transaction);
    void deleteTransaction(int id);
 // 날짜별로 거래 내역을 검색하는 메소드
    List<Transaction> getTransactionsByDate(Date date);
}

