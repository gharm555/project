package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.itwill.transaction.controller.TransactionDao;
import com.itwill.transaction.model.Transaction;
import com.toedter.calendar.JCalendar;

public class Main {
	private static final String[] COLUMN_NAMES = { "카테고리", "종류", "금액" };

	private JFrame frame;
	private JCalendar calendar;
	private JButton updateButton;
	private JTable transactionsTable;
	private TransactionDao transactionDao;
	private JPanel detailsPanel;
	private Date selectedDate;
	private TransactionDao dao = TransactionDao.getInstance();
	private JTable detailsTable;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
		initTable();
	}

	private void initTable() {
		List<Transaction> t = dao.read();
		resetTableModel(t);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Transaction Management");
		frame.setBounds(100, 100, 850, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		calendar = new JCalendar();
		calendar.setBounds(125, 36, 600, 400);
		frame.getContentPane().add(calendar);

		updateButton = new JButton("Update");
		updateButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		updateButton.setBounds(750, 370, 70, 70);
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddFrame.showAddFrame(frame);
			}
		});
		frame.getContentPane().add(updateButton);

		detailsPanel = new JPanel();
		detailsPanel.setLayout(new BorderLayout());
		detailsPanel.setBounds(125, 448, 600, 112); // 위치와 크기 조정 필요
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));

		frame.getContentPane().add(detailsPanel);

		scrollPane = new JScrollPane();
		detailsPanel.add(scrollPane, BorderLayout.CENTER);

		detailsTable = new JTable();
		tableModel = new DefaultTableModel(null, COLUMN_NAMES);
		detailsTable.setModel(tableModel);
		scrollPane.setViewportView(detailsTable);

		calendar.addPropertyChangeListener("calendar", event -> {
			if ("calendar".equals(event.getPropertyName())) {
				selectedDate = calendar.getDate();
				displayTransactionsForDate(selectedDate);
			}
		});

	}
	private void displayTransactionsForDate(Date date) {
		List<Transaction> transactions = dao.getTransactionsByDate(date);
		resetTableModel(transactions);
	}
	private void resetTableModel(List<Transaction> transaction) {
		tableModel = new DefaultTableModel(null, COLUMN_NAMES);
		for (Transaction t : transaction) {
			Object[] row = { t.getCategory(), t.getType(), t.getAmount() };
			tableModel.addRow(row);
		}
		detailsTable.setModel(tableModel);
	}

}
