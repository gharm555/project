package com.itwill.transaction.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.itwill.transaction.controller.TransactionDao;
import com.itwill.transaction.model.Transaction;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class AddFrame extends JFrame {
    public interface AddNotify {
        void addSuccess();
    }

    private static final String[] PAYMENT_CATEGORIES = { "식비", "교통비", "공과금", "의료비", "문화생활", "생필품", "교육", "구독비", "통신비",
            "운동", "여행", "술", "의류", "육아", "기타" };
    private static final String[] INCOME_CATEGORIES = { "월급", "부수입", "용돈", "보너스", "기타" };
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Component parent;
    private TransactionDao dao = TransactionDao.getInstance();
    private JTextField textField_1;
    private JLabel lblDate;
    private JComboBox<String> category;
    private JLabel lblNote;
    private JButton btnBack;
    private JButton btnAdd;
    private JLabel lblDate_1;
    private JTextField income;
    private JComboBox<String> category_1;
    private JLabel lblMemo_1;
    private JButton btnBack_1;
    private JButton btnAdd_1;
    private JTextField paymentamount;
    private JTextField noteField;
    private AddNotify app;
    private Date date;
    private ImageIcon checkIcon = new ImageIcon(getClass().getResource("/checkicon.png"));
    private ImageIcon prevIcon = new ImageIcon(getClass().getResource("/previcon.png"));
    private JLabel lblAmount;
    private JLabel lblAmount_1;

    /**
     * Launch the application.
     * 
     * @param main
     * @param frame
     */
    public static void showAddFrame(Component parent, AddNotify app, Date date) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddFrame frame = new AddFrame(parent, app, date);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AddFrame(Component parent, AddNotify app, Date date) {
        this.parent = parent;
        this.app = app;
        this.date = date;
        init();
        setDate(date);
    }

    /**
     * Create the frame.
     */
    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 650);
        setLocationRelativeTo(parent);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 탭 생성
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(12, 6, 810, 610);
        contentPane.add(tabbedPane);
        final DefaultComboBoxModel<String> paymentModel = new DefaultComboBoxModel<String>(PAYMENT_CATEGORIES);

        // 지출 탭
        JPanel spendPanel = new JPanel();
        spendPanel.setLayout(null);
        spendPanel.setFont(new Font("D2Coding", Font.PLAIN, 13));
        tabbedPane.addTab("지출", spendPanel);

        lblDate = new JLabel("");
        lblDate.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblDate.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate.setBounds(252, 10, 300, 80);
        spendPanel.add(lblDate);

        paymentamount = new JTextField();
        paymentamount.setFont(new Font("D2Coding", Font.PLAIN, 13));
        paymentamount.setBounds(290, 128, 420, 80);
        spendPanel.add(paymentamount);
        paymentamount.setColumns(10);

        category = new JComboBox<>();
        category.setFont(new Font("D2Coding", Font.PLAIN, 13));
        category.setBounds(122, 246, 560, 80);
        category.setModel(paymentModel);
        spendPanel.add(category);

        lblNote = new JLabel("Memo");
        lblNote.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblNote.setHorizontalAlignment(SwingConstants.CENTER);
        lblNote.setBounds(95, 363, 100, 80);
        spendPanel.add(lblNote);

        noteField = new JTextField();
        noteField.setFont(new Font("D2Coding", Font.PLAIN, 13));
        noteField.setBounds(290, 364, 420, 80);
        spendPanel.add(noteField);
        noteField.setColumns(10);

        btnBack = new JButton(prevIcon);
        btnBack.setBounds(662, 510, 48, 48);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                parent.setVisible(true);
            }
        });
        spendPanel.add(btnBack);

        btnAdd = new JButton(checkIcon);
        btnAdd.setBounds(735, 510, 48, 48);
        // btnAdd에 ActionListener 추가
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 입력 데이터 추출
                String amountText = paymentamount.getText().trim();
                String categorySelected = (String) category.getSelectedItem();
                String noteText = noteField.getText().trim();

                // 데이터 검증
                if (amountText.isEmpty() || categorySelected == null) {
                    JOptionPane.showMessageDialog(null, "금액을 입력해주세요.");
                    return;
                }
                if (amountText.length() > 10) {
                    JOptionPane.showMessageDialog(null, "금액은 10자리를 초과할 수 없습니다.");
                    return;
                }
                if (amountText.equals(0)) {
                    JOptionPane.showMessageDialog(null, "0원은 입력할 수 없습니다.");
                    return;
                }
                if (Integer.parseInt(amountText) < 0) {
                    JOptionPane.showMessageDialog(null, "음수는 입력할 수 없습니다.");
                    return;
                }
                if (noteText.length() > 255) {
                    JOptionPane.showMessageDialog(null, "메모는 255자를 초과할 수 없습니다.");
                    return;
                }

                // 데이터베이스에 저장
                try {
                    Transaction transaction = new Transaction();
                    transaction.setType("지출");
                    amountText = paymentamount.getText().trim();
                    int amount = Integer.parseInt(amountText);
                    transaction.setAmount(amount);
                    transaction.setCategory(categorySelected);
                    transaction.setNotes(noteText);
                    transaction.setDate(date); // 현재 날짜 사용, 필요에 따라 다른 날짜 지정 가능

                    dao.create(transaction); // TransactionDao의 create 메소드를 호출하여 데이터 저장
                    JOptionPane.showMessageDialog(null, "지출이 성공적으로 추가되었습니다.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "데이터 저장 중 오류가 발생했습니다.");
                    ex.printStackTrace();
                }
                app.addSuccess();
                dispose();
                parent.setVisible(true);
            }
        });
        spendPanel.add(btnAdd);

        // 수입 탭
        JPanel incomePanel = new JPanel();
        incomePanel.setLayout(null);
        incomePanel.setFont(new Font("D2Coding", Font.PLAIN, 13));
        tabbedPane.addTab("수입", incomePanel);

        lblDate_1 = new JLabel("");
        lblDate_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate_1.setBounds(252, 10, 300, 80);
        incomePanel.add(lblDate_1);

        income = new JTextField();
        income.setFont(new Font("D2Coding", Font.PLAIN, 13));
        income.setColumns(10);
        income.setBounds(290, 128, 420, 80);
        incomePanel.add(income);

        btnBack_1 = new JButton(prevIcon);
        btnBack_1.setBounds(662, 510, 48, 48);

        btnBack_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                parent.setVisible(true);
            }
        });
        incomePanel.add(btnBack_1);

        category_1 = new JComboBox<>();
        category_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        category_1.setBounds(122, 246, 560, 80);
        final DefaultComboBoxModel<String> incomeModel = new DefaultComboBoxModel<String>(INCOME_CATEGORIES);
        category_1.setModel(incomeModel);
        incomePanel.add(category_1);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        textField_1.setColumns(10);
        textField_1.setBounds(290, 364, 420, 80);
        incomePanel.add(textField_1);

        lblMemo_1 = new JLabel("Memo");
        lblMemo_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblMemo_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblMemo_1.setBounds(95, 363, 100, 80);
        incomePanel.add(lblMemo_1);

        btnAdd_1 = new JButton(checkIcon);
        btnAdd_1.setBounds(735, 510, 48, 48);
        incomePanel.add(btnAdd_1);
        btnAdd_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 입력 데이터 추출
                String amountText = income.getText().trim();
                String categorySelected = (String) category_1.getSelectedItem();
                String noteText = textField_1.getText().trim();

                // 데이터 검증
                if (amountText.isEmpty() || categorySelected == null) {
                    JOptionPane.showMessageDialog(null, "금액을 입력해주세요.");
                    return;
                }
                if (amountText.length() > 10) {
                    JOptionPane.showMessageDialog(null, "금액은 10자리를 초과할 수 없습니다.");
                    return;
                }
                if (amountText.equals(0)) {
                    JOptionPane.showMessageDialog(null, "0원은 입력할 수 없습니다.");
                    return;
                }
                if (Integer.parseInt(amountText) < 0) {
                    JOptionPane.showMessageDialog(null, "음수는 입력할 수 없습니다.");
                    return;
                }
                if (noteText.length() > 255) {
                    JOptionPane.showMessageDialog(null, "메모는 255자를 초과할 수 없습니다.");
                    return;
                }

                // 데이터베이스에 저장
                try {
                    Transaction transaction = new Transaction();
                    transaction.setType("수입");
                    amountText = income.getText().trim();
                    int amount = Integer.parseInt(amountText);
                    transaction.setAmount(amount);
                    transaction.setCategory(categorySelected);
                    transaction.setNotes(noteText);
                    transaction.setDate(date); // 현재 날짜 사용, 필요에 따라 다른 날짜 지정 가능

                    dao.create(transaction); // TransactionDao의 create 메소드를 호출하여 데이터 저장
                    JOptionPane.showMessageDialog(null, "수입이 성공적으로 추가되었습니다.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "데이터 저장 중 오류가 발생했습니다.");
                    ex.printStackTrace();
                }
                app.addSuccess();
                dispose();
                parent.setVisible(true);
            }
        });
        btnBack_1.setBorderPainted(false);
        btnBack_1.setContentAreaFilled(false);
        btnBack_1.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setFocusPainted(false);

        lblAmount = new JLabel("Amonut");
        lblAmount.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmount.setBounds(95, 128, 100, 80);
        spendPanel.add(lblAmount);
        btnAdd_1.setBorderPainted(false);
        btnAdd_1.setContentAreaFilled(false);
        btnAdd_1.setFocusPainted(false);

        lblAmount_1 = new JLabel("Amonut");
        lblAmount_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblAmount_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmount_1.setBounds(95, 128, 100, 80);
        incomePanel.add(lblAmount_1);
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(sdf.format(date));
        lblDate_1.setText(sdf.format(date));
    }
}
