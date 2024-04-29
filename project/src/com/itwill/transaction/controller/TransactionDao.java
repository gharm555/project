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
		String sqlBase = "INSERT INTO Transactions (Type, Amount, Category_ID, Transaction_Date, Description) VALUES (?, ?, ?, ?, ?)";
		String sqlIncome = "INSERT INTO IncomeTransactions (Type, Amount, Category_ID, Transaction_Date, Description) VALUES (?, ?, ?, ?, ?)";
		String sqlExpense = "INSERT INTO ExpenseTransactions (Type, Amount, Category_ID, Transaction_Date, Description) VALUES (?, ?, ?, ?, ?)";
		
		String selectedSql = sqlBase; // 기본 SQL

		// 수입이나 지출에 따라 다른 테이블에 삽입
		if (transaction.getType().equals("수입")) {
			selectedSql = sqlIncome;
		} else if (transaction.getType().equals("지출")) {
			selectedSql = sqlExpense;
		}

		// 카테고리에 따라 SQL을 추가 조정할 수 있습니다.
		// 예: if (transaction.getCategory().equals("특정 카테고리")) { ... }

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 PreparedStatement stmt = conn.prepareStatement(selectedSql)) {
			
			stmt.setString(1, transaction.getType());
			stmt.setDouble(2, transaction.getAmount());
			stmt.setString(3, transaction.getCategory());
			stmt.setDate(4, new java.sql.Date(transaction.getDate().getTime()));
			stmt.setString(5, transaction.getNotes());
			
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating transaction failed, no rows affected.");
			}
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
		String type = rs.getString(COL_TYPE);
		String category = rs.getString(COL_CATEGORY);
		Date Date = rs.getDate(COL_TRANSACTION_DATE);
		String notes = rs.getString(COL_NOTES);

		Transaction t = new Transaction(id, type, category, Date, notes);

		return t;
	}

	private static final String SELECT_BY_DATE = "SELECT * FROM Transactions WHERE Transaction_Date = ?";

	public List<Transaction> getTransactionsByDate(Date date) {
		List<Transaction> transactions = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SELECT_BY_DATE);
			stmt.setDate(1, new java.sql.Date(date.getTime()));
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				transactions.add(makeTransactionFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return transactions;
	}
	
	private static final String SQL_UPDATE_TRANSACTION = "UPDATE Transactions SET Type=?, Amount=?, Category_ID=?, Transaction_Date=?, Description=? WHERE ID=?";

	public void updateTransaction(Transaction transaction) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try{
					conn = DriverManager.getConnection(URL, USER, PASSWORD);
					stmt = prepareUpdateStatement(conn, transaction);
					stmt.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
				closeResources(conn, stmt);
			}
	}
	
	private PreparedStatement prepareUpdateStatement(Connection conn, Transaction transaction) throws SQLException {
			PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_TRANSACTION);
			stmt.setString(1, transaction.getType());
			stmt.setDouble(2, transaction.getAmount());
			stmt.setString(3, transaction.getCategory());
			stmt.setDate(4, new java.sql.Date(transaction.getDate().getTime()));
			stmt.setString(5, transaction.getNotes());
			stmt.setInt(6, transaction.getId());
			return stmt;
	}
}
