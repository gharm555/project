package com.itwill.transaction.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TransactionTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Type", "Amount", "Category ID", "Date", "Description"};
    private ArrayList<Transaction> transactions;

    public TransactionTableModel() {
        this.transactions = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = transactions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return transaction.getId();
            case 1:
                return transaction.getType();
            case 2:
                return transaction.getAmount();
            case 3:
                return transaction.getCategoryId();
            case 4:
                return transaction.getDate();
            case 5:
                return transaction.getDescription();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        fireTableDataChanged();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        fireTableRowsInserted(transactions.size() - 1, transactions.size() - 1);
    }

    public void removeTransaction(int rowIndex) {
        transactions.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
