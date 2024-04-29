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

public class AddFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Component parent;
	private TransactionDao dao = TransactionDao.getInstance();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	

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
		setBounds(100, 100, 757, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 탭 생성
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 720, 500);
        contentPane.add(tabbedPane);
		
		// 지출 탭
        JPanel spendPanel = new JPanel();
        spendPanel.setLayout(null);
        tabbedPane.addTab("지출", spendPanel);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(288, 25, 122, 38);
        spendPanel.add(lblNewLabel);
        
        textField = new JTextField();
        textField.setBounds(280, 80, 130, 38);
        spendPanel.add(textField);
        textField.setColumns(10);
        
        JButton btnNewButton = new JButton("New button");
        btnNewButton.setBounds(78, 142, 117, 29);
        spendPanel.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("New button");
        btnNewButton_1.setBounds(216, 142, 117, 29);
        spendPanel.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("New button");
        btnNewButton_2.setBounds(354, 142, 117, 29);
        spendPanel.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("New button");
        btnNewButton_3.setBounds(492, 142, 117, 29);
        spendPanel.add(btnNewButton_3);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(222, 209, 255, 27);
        spendPanel.add(comboBox);
        
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(93, 288, 61, 16);
        spendPanel.add(lblNewLabel_1);
        
        textField_1 = new JTextField();
        textField_1.setBounds(203, 283, 130, 26);
        spendPanel.add(textField_1);
        textField_1.setColumns(10);
        
        JButton btnNewButton_4 = new JButton("New button");
        btnNewButton_4.setBounds(444, 407, 117, 29);
        spendPanel.add(btnNewButton_4);
        
        JButton btnNewButton_4_1 = new JButton("New button");
        btnNewButton_4_1.setBounds(576, 407, 117, 29);
        spendPanel.add(btnNewButton_4_1);

        // 수입 탭
        JPanel incomePanel = new JPanel();
        incomePanel.setLayout(null);
        tabbedPane.addTab("수입", incomePanel);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(278, 45, 130, 38);
        incomePanel.add(textField_2);
        
        JButton btnNewButton_5 = new JButton("New button");
        btnNewButton_5.setBounds(54, 103, 117, 29);
        incomePanel.add(btnNewButton_5);
        
        JButton btnNewButton_1_1 = new JButton("New button");
        btnNewButton_1_1.setBounds(192, 103, 117, 29);
        incomePanel.add(btnNewButton_1_1);
        
        JButton btnNewButton_2_1 = new JButton("New button");
        btnNewButton_2_1.setBounds(330, 103, 117, 29);
        incomePanel.add(btnNewButton_2_1);
        
        JButton btnNewButton_3_1 = new JButton("New button");
        btnNewButton_3_1.setBounds(468, 103, 117, 29);
        incomePanel.add(btnNewButton_3_1);
        
        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(198, 170, 255, 27);
        incomePanel.add(comboBox_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("New label");
        lblNewLabel_1_1.setBounds(69, 249, 61, 16);
        incomePanel.add(lblNewLabel_1_1);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(179, 244, 130, 26);
        incomePanel.add(textField_3);
        
        JButton btnNewButton_4_2 = new JButton("New button");
        btnNewButton_4_2.setBounds(420, 368, 117, 29);
        incomePanel.add(btnNewButton_4_2);
        
        JButton btnNewButton_4_1_1 = new JButton("New button");
        btnNewButton_4_1_1.setBounds(552, 368, 117, 29);
        incomePanel.add(btnNewButton_4_1_1);
        
        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setBounds(302, 13, 61, 16);
        incomePanel.add(lblNewLabel_2);

	}
}
