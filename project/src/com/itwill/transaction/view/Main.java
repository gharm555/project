package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.itwill.transaction.controller.TransactionDao;
import com.itwill.transaction.controller.TransactionDaoImpl;
import com.itwill.transaction.model.Transaction;
import com.itwill.transaction.model.TransactionTableModel;
import com.toedter.calendar.JCalendar;

public class Main {
	private JFrame frame;
	private JCalendar calendar;
	private JButton updateButton;
	private JTable transactionsTable;
	private TransactionTableModel tableModel;
	private TransactionDao transactionDao;
	private InformationDialog infoDialog;
	private JPanel detailsPanel;
    private JLabel detailsLabel;

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
		transactionDao = new TransactionDaoImpl(); // DAO 초기화
		initialize();
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
		frame.getContentPane().add(updateButton);

		calendar.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (e.getClickCount() == 1) {
	                // 단일 클릭 - 선택된 날짜 처리
	                java.util.Date selectedDate = calendar.getDate();
	                // 단일 클릭 시 할 작업을 여기에 구현합니다.
	            } else if (e.getClickCount() == 2) {
	                // 더블 클릭 - 정보 표시
	                java.util.Date selectedDate = calendar.getDate();
	                displayTransactionsForDate(selectedDate); // 더블 클릭 시 정보 표시
	            }
	        }
	    });

		updateButton.addActionListener(e -> {
			int selectedRow = transactionsTable.getSelectedRow();
			if (selectedRow != -1) {
				Transaction selectedTransaction = tableModel.getTransactionAt(selectedRow);
				showTransactionEditDialog(selectedTransaction);
			}
		});

		infoDialog = new InformationDialog(frame); // 정보 다이얼로그 인스턴스화

		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if ("calendar".equals(event.getPropertyName())) {
					java.util.Date selectedDate = calendar.getDate();
					displayTransactionsForDate(selectedDate); // 선택된 날짜의 거래 내역 조회
				}
			}
		});

		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 수정 로직 구현...
			}
		});
		 detailsPanel = new JPanel();
	        detailsPanel.setLayout(new BorderLayout());
	        detailsPanel.setBounds(125, 460, 600, 100); // 위치와 크기 조정 필요
	        detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));

	        detailsLabel = new JLabel(" Click on a date to see transactions ");
	        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        detailsPanel.add(detailsLabel, BorderLayout.CENTER);

	        frame.getContentPane().add(detailsPanel);

	}

	// 거래를 수정하기 위한 인터페이스를 표시하는 메서드
	public void showTransactionEditDialog(Transaction transaction) {
		// TransactionEditDialog는 사용자가 선택한 거래를 수정할 수 있는 커스텀 다이얼로그입니다.
		// 이 예제에서는 단순히 정보를 표시하는 다이얼로그로 사용하고 있습니다.
		String info = "ID: " + transaction.getId() + "\nType: " + transaction.getType() + "\nAmount: "
				+ transaction.getAmount() + "\n...";
		infoDialog.displayInformation(info);
	}

	// 달력에서 날짜 선택 시 호출될 메소드
	public void displayTransactionsForDate(java.util.Date date) {
        List<Transaction> transactionsForDate = transactionDao.getTransactionsByDate(date);
        if (!transactionsForDate.isEmpty()) {
            // 여기서 첫 번째 거래의 세부사항만 표시합니다. 원하는 대로 수정 가능
            Transaction transaction = transactionsForDate.get(0);
            String details = "<html><body><strong>Date:</strong> " + transaction.getDate() +
                             "<br/><strong>Amount:</strong> " + transaction.getAmount() +
                             "<br/><strong>Description:</strong> " + transaction.getDescription() +
                             "</body></html>";
            detailsLabel.setText(details);
        } else {
            detailsLabel.setText(" No transactions for this date. ");
        }
        detailsPanel.revalidate(); // 세부 정보 패널 업데이트
    }

}
