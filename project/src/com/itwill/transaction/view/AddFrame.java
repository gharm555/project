package com.itwill.transaction.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.itwill.transaction.controller.TransactionDao;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class AddFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Component parent;
	private TransactionDao dao = TransactionDao.getInstance();
	private JTextField textField_1;
	private JLabel lblDate;
	private JComboBox category;
	private JLabel lblMemo;
	private JButton btnBack;
	private JButton btnAdd;
	private JLabel lblDate_1;
	private JTextField textField;
	private JComboBox category_1;
	private JLabel lblMemo_1;
	private JButton btnBack_1;
	private JButton btnAdd_1;
	private JTextField paymentamount;
	private JTextField memoField;
	

	/**
	 * Launch the application.
	 * 
	 * @param main
	 * @param frame
	 */
	public static void showAddFrame(Component parent) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFrame frame = new AddFrame(parent);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private AddFrame(Component parent) {
		this.parent = parent;
		init();
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 693, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 탭 생성
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(6, 6, 681, 474);
        contentPane.add(tabbedPane);
		
		// 지출 탭
        JPanel spendPanel = new JPanel();
        spendPanel.setLayout(null);
        tabbedPane.addTab("지출", spendPanel);
        
        lblDate = new JLabel("");
        lblDate.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate.setBounds(271, 29, 122, 38);
        spendPanel.add(lblDate);
        
        paymentamount = new JTextField();
        paymentamount.setBounds(184, 79, 291, 72);
        spendPanel.add(paymentamount);
        paymentamount.setColumns(10);
        
        category = new JComboBox();
        category.setBounds(141, 163, 378, 62);
        spendPanel.add(category);
        
        lblMemo = new JLabel("Memo");
        lblMemo.setHorizontalAlignment(SwingConstants.CENTER);
        lblMemo.setBounds(184, 237, 104, 62);
        spendPanel.add(lblMemo);
        
        memoField = new JTextField();
        memoField.setBounds(351, 237, 168, 62);
        spendPanel.add(memoField);
        memoField.setColumns(10);
        
        btnBack = new JButton("<");
        btnBack.setBounds(402, 393, 117, 29);
        spendPanel.add(btnBack);
        
        btnAdd = new JButton("v");
        btnAdd.setBounds(537, 393, 117, 29);
        spendPanel.add(btnAdd);

        // 수입 탭
        JPanel incomePanel = new JPanel();
        incomePanel.setLayout(null);
        tabbedPane.addTab("수입", incomePanel);
        
        lblDate_1 = new JLabel("");
        lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate_1.setBounds(269, 29, 122, 38);
        incomePanel.add(lblDate_1);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(184, 79, 291, 72);
        incomePanel.add(textField);
        
        category_1 = new JComboBox();
        category_1.setBounds(141, 163, 378, 62);
        incomePanel.add(category_1);
        
        lblMemo_1 = new JLabel("Memo");
        lblMemo_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblMemo_1.setBounds(184, 237, 104, 62);
        incomePanel.add(lblMemo_1);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(351, 237, 168, 62);
        incomePanel.add(textField_1);
        
        btnBack_1 = new JButton("<");
        btnBack_1.setBounds(402, 393, 117, 29);
        incomePanel.add(btnBack_1);
        
        btnAdd_1 = new JButton("v");
        btnAdd_1.setBounds(537, 393, 117, 29);
        incomePanel.add(btnAdd_1);

	}
}
