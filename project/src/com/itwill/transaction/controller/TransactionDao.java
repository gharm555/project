package com.itwill.transaction.controller;

import java.util.List;

import com.itwill.transaction.model.Transaction;

public interface TransactionDao {
    void insertTransaction(Transaction transaction);
    Transaction getTransactionById(int id);
    List<Transaction> getAllTransactions();
    void updateTransaction(Transaction transaction);
    void deleteTransaction(int id);
}

