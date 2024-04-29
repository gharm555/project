package com.itwill.transaction.controller;

import static com.itwill.transaction.Jdbc.*;
import static com.itwill.transaction.model.Transaction.Entity.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itwill.transaction.model.Transaction;

import oracle.jdbc.OracleDriver;

public class TransactionDao {
	// -----------------------------
	private static TransactionDao instance = null;

	private TransactionDao() {
		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static TransactionDao getInstance() {
		if (instance == null) {
			instance = new TransactionDao();
		}
		return instance;
	}

	// ---------------------------
	private void closeResources(Connection conn, Statement smtm, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (smtm != null) {
				smtm.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void closeResources(Connection conn, Statement smtm) {
		closeResources(conn, smtm, null);
	}

	private static final String sql = "INSERT INTO Transactions (Type, Amount, Category_ID, Transaction_Date, Description) VALUES (?, ?, ?, ?, ?)";

	public void insertTransaction(Transaction transaction) {
		int result = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(sql);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static final String SQL_SELECT_ALL = String.format("select * from %s order by %s desc", TBL_Transaction,
			COL_ID);

	public List<Transaction> read() {
		List<Transaction> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SQL_SELECT_ALL);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction t = makeTransactionFromResultSet(rs);
				result.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	private Transaction makeTransactionFromResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt(COL_ID);
		String payment = rs.getString(COL_PAYMENT);
		String income = rs.getString(COL_INCOME);
		String categoryName = rs.getString(COL_CATEGORY_NAME);
		Date exDate = rs.getDate(COL_EXPENSE_DATE);
		Date indate = rs.getDate(COL_INCOME_DATE);
		String notes = rs.getString(COL_NOTES);

		Transaction t = new Transaction(id, payment, income, categoryName, exDate, indate, notes);

		return t;
	}

	private static final String SELECT_BY_DATE = "SELECT * FROM Transactions WHERE Transaction_Date = ?";

	public List<Transaction> getTransactionsByDate(Date date) {
		List<Transaction> transactions = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		try  {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SELECT_BY_DATE);
			stmt.setDate(1, new java.sql.Date(date.getTime()));
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					transactions.add(makeTransactionFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

}
