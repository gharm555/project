package com.itwill.transaction.controller;

import static com.itwill.transaction.Jdbc.*;
import static com.itwill.transaction.model.Transaction.Entity.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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

	public void create(Transaction transaction) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", TBL_Transaction,
					COL_TYPE, COL_CATEGORY, COL_AMOUNT, COL_TRANSACTION_DATE, COL_NOTES);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, transaction.getType());
			stmt.setString(2, transaction.getCategory());
			stmt.setInt(3, transaction.getAmount());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = sdf.format(transaction.getDate());
			stmt.setString(4, formattedDate);
			stmt.setString(5, transaction.getNotes());

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating transaction failed, no rows affected.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
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
		} finally {
			closeResources(conn, stmt, rs);
		}

		return result;
	}

	private Transaction makeTransactionFromResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt(COL_ID);
		String type = rs.getString(COL_TYPE);
		String category = rs.getString(COL_CATEGORY);
		Date date = rs.getDate(COL_TRANSACTION_DATE);
		int amount = rs.getInt(COL_AMOUNT);
		String notes = rs.getString(COL_NOTES);

		Transaction t = new Transaction(id, type, category, date, amount, notes);
		return t;
	}

	private static final String SELECT_BY_DATE = String
			.format("SELECT * FROM %s WHERE TransactionDate = to_date(?,'YYYY-MM-DD')", TBL_Transaction);

	public List<Transaction> getTransactionsByDate(Date date) {
		List<Transaction> transactions = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SELECT_BY_DATE);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = sdf.format(date);
			stmt.setString(1, formattedDate);
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

	public void update(Transaction transaction) {

	}

	public static final String SQL_DELETE = String.format("delete from %s where %s = ?", TBL_Transaction, COL_ID);

	public int delete(Integer id) {
		int result = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, id);
			result = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
		}

		return result;
	}
}
