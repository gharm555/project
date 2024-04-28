package com.itwill.transaction.controller;

import java.util.List;

import com.itwill.transaction.model.Transaction;
import com.itwill.transaction.view.TransactionView;

public class TransactionController {
    private TransactionDao transactionDao;
    private TransactionView view;

    public TransactionController(TransactionDao transactionDao, TransactionView view) {
        this.transactionDao = transactionDao;
        this.view = view;
        initView();
    }

    private void initView() {
        view.setAddButtonActionListener(e -> addTransaction());
        view.setUpdateButtonActionListener(e -> updateTransaction());
        view.setDeleteButtonActionListener(e -> deleteTransaction());
        updateView();
    }

    private void addTransaction() {
        Transaction transaction = view.getTransactionFromForm();
        transactionDao.insertTransaction(transaction);
        updateView();
    }

    private void updateTransaction() {
        Transaction transaction = view.getTransactionFromForm();
        transactionDao.updateTransaction(transaction);
        updateView();
    }

    private void deleteTransaction() {
        int id = view.getSelectedTransactionId();
        transactionDao.deleteTransaction(id);
        updateView();
    }

    private void updateView() {
        List<Transaction> transactions = transactionDao.getAllTransactions();
        view.updateTable(transactions);
    }
}


