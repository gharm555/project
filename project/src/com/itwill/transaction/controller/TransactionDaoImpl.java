package com.itwill.transaction.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itwill.transaction.model.Transaction;

public class TransactionDaoImpl implements TransactionDao {
	private Connection connection;

	public TransactionDaoImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insertTransaction(Transaction transaction) {

		String sql = "INSERT INTO Transactions (ID, Type, Amount, Category_ID, Date, Description) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, transaction.getId());
			statement.setString(2, transaction.getType());
			statement.setDouble(3, transaction.getAmount());
			statement.setInt(4, transaction.getCategoryId());
			statement.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
			statement.setString(6, transaction.getDescription());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Transaction getTransactionById(int id) {
		Transaction transaction = null;
		String sql = "SELECT * FROM Transactions WHERE ID = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				transaction = new Transaction(resultSet.getInt("ID"), resultSet.getString("Type"),
						resultSet.getDouble("Amount"), resultSet.getInt("Category_ID"), resultSet.getDate("Date"),
						resultSet.getString("Description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transaction;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		String sql = "SELECT * FROM Transactions";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Transaction transaction = new Transaction(resultSet.getInt("ID"), resultSet.getString("Type"),
						resultSet.getDouble("Amount"), resultSet.getInt("Category_ID"), resultSet.getDate("Date"),
						resultSet.getString("Description"));
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	@Override
	public void updateTransaction(Transaction transaction) {
		String sql = "UPDATE Transactions SET Type = ?, Amount = ?, Category_ID = ?, Date = ?, Description = ? WHERE ID = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, transaction.getType());
			statement.setDouble(2, transaction.getAmount());
			statement.setInt(3, transaction.getCategoryId());
			statement.setDate(4, new java.sql.Date(transaction.getDate().getTime()));
			statement.setString(5, transaction.getDescription());
			statement.setInt(6, transaction.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTransaction(int id) {
		String sql = "DELETE FROM Transactions WHERE ID = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
