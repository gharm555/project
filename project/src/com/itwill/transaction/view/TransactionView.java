package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.itwill.transaction.model.Transaction;
import com.itwill.transaction.model.TransactionTableModel;

public class TransactionView extends JFrame {
	private JTextField idField, typeField, amountField, categoryIdField, descriptionField;
	private JFormattedTextField dateField; 
	private JButton addButton, updateButton, deleteButton;
	private JTable transactionTable;
	private TransactionTableModel tableModel;
	public TransactionView() {
		setTitle("Transaction Manager");
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// 메뉴 바 설정
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		// 테이블 모델 설정
		tableModel = new TransactionTableModel();
		transactionTable = new JTable(tableModel);

		// 입력 폼
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(6, 2));
		formPanel.add(new JLabel("ID:"));
		idField = new JTextField(10);
		formPanel.add(idField);
		formPanel.add(new JLabel("Type:"));
		typeField = new JTextField(10);
		formPanel.add(typeField);
		formPanel.add(new JLabel("Amount:"));
		amountField = new JTextField(10);
		formPanel.add(amountField);
		formPanel.add(new JLabel("Category ID:"));
		categoryIdField = new JTextField(10);
		formPanel.add(categoryIdField);
		formPanel.add(new JLabel("Description:"));
		descriptionField = new JTextField(10);
		formPanel.add(descriptionField);

		// 버튼
		addButton = new JButton("Add");
		updateButton = new JButton("Update");
		deleteButton = new JButton("Delete");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);

		// 메인 윈도우에 컴포넌트 추가
		add(new JScrollPane(transactionTable), BorderLayout.CENTER);
		add(formPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);

		// 이벤트 리스너 설정 (예시)
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 추가 버튼 로직
			}
		});


	}

	public void setAddButtonActionListener(ActionListener listener) {
		addButton.addActionListener(listener);
	}

	public void setUpdateButtonActionListener(ActionListener listener) {
		updateButton.addActionListener(listener);
	}

	public void setDeleteButtonActionListener(ActionListener listener) {
		deleteButton.addActionListener(listener);
	}

	public Transaction getTransactionFromForm() {
	    try {
	        int id = Integer.parseInt(idField.getText());
	        String type = typeField.getText();
	        double amount = Double.parseDouble(amountField.getText());
	        int categoryId = Integer.parseInt(categoryIdField.getText());
	        String description = descriptionField.getText();

	        // 날짜 필드에서 텍스트를 가져와 Date 객체로 파싱
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = formatter.parse(dateField.getText()); // dateField는 날짜 입력을 받는 JTextField 또는 JFormattedTextField

	        return new Transaction(id, type, amount, categoryId, date, description);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 오류 처리: 입력 형식 오류, 숫자 변환 오류 등에 대한 처리
	        // 오류 발생시 null 반환 또는 사용자에게 입력 오류를 알리는 방법 고려
	        return null;
	    }
	}

	public int getSelectedTransactionId() {
	    int selectedRow = transactionTable.getSelectedRow();
	    if (selectedRow >= 0) {
	        return (int) transactionTable.getValueAt(selectedRow, 0);  // ID가 첫 번째 컬럼에 위치한다고 가정
	    }
	    return -1; // 선택되지 않았을 경우
	}


	public void updateTable(List<Transaction> transactions) {
		// 테이블 데이터를 업데이트하는 로직
	}
}
