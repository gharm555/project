package com.itwill.transaction.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UpdateFrame extends JFrame {
    public interface UpdateNotify {
        void UpdateSuccess();
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
    private JButton btnUpdate;
    private JLabel lblDate_1;
    private JTextField income;
    private JComboBox<String> category_1;
    private JLabel lblMemo_1;
    private JButton btnBack_1;
    private JButton btnUpdate_1;
    private JTextField paymentamount;
    private JTextField noteField;
    private UpdateNotify app;
    private Date date;
    private Transaction transaction;
    private JTabbedPane tabbedPane;
    private JPanel spendPanel;
    private JPanel incomePanel;
    private ImageIcon checkIcon = new ImageIcon("project/icon/checkicon.png");
    private ImageIcon prevIcon = new ImageIcon("project/icon/previcon.png");
    private JLabel lblAmount_1;
    private int id;

    /**
     * Launch the application.
     * 
     * @param main
     * @param frame
     */
    public static void showUpdateFrame(Component parent, UpdateNotify app, Transaction transaction, Date date, int id) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UpdateFrame frame = new UpdateFrame(parent, app, transaction, date, id);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UpdateFrame(Component parent, UpdateNotify app, Transaction transaction, Date date, int id) {
        this.parent = parent;
        this.app = app;
        this.transaction = transaction;
        this.date = date;
        this.id = id;
        init();
        setDate(date);
        populateFields(transaction);
    }

    private void populateFields(Transaction transaction) {
        if (transaction != null) {
            if (transaction.getType().equals("지출")) {
                paymentamount.setText(String.valueOf(transaction.getAmount()));
                category.setSelectedItem(transaction.getCategory());
                noteField.setText(transaction.getNotes());
            } else {
                tabbedPane.setSelectedIndex(1);
                income.setText(String.valueOf(transaction.getAmount()));
                category_1.setSelectedItem(transaction.getCategory());
                textField_1.setText(transaction.getNotes());
            }
            // 날짜 설정, 다른 필드가 있다면 여기에 추가
        }
    }

    /**
     * Create the frame.
     */
    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 650);
        setLocationRelativeTo(parent);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 탭 생성
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(12, 10, 810, 606);
        contentPane.add(tabbedPane);

        // 지출 탭
        spendPanel = new JPanel();
        spendPanel.setLayout(null);
        tabbedPane.addTab("지출", spendPanel);

        lblDate = new JLabel("");
        lblDate.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblDate.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate.setBounds(252, 10, 300, 80);
        spendPanel.add(lblDate);

        paymentamount = new JTextField();
        paymentamount.setFont(new Font("D2Coding", Font.PLAIN, 13));
        paymentamount.setBounds(290, 129, 420, 80);
        spendPanel.add(paymentamount);
        paymentamount.setColumns(10);

        category = new JComboBox<>();
        category.setFont(new Font("D2Coding", Font.PLAIN, 13));
        category.setBounds(122, 248, 560, 80);
        final DefaultComboBoxModel<String> paymentModel = new DefaultComboBoxModel<String>(PAYMENT_CATEGORIES);
        category.setModel(paymentModel);
        spendPanel.add(category);

        lblNote = new JLabel("Memo");
        lblNote.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblNote.setHorizontalAlignment(SwingConstants.CENTER);
        lblNote.setBounds(95, 368, 100, 80);
        spendPanel.add(lblNote);

        noteField = new JTextField();
        noteField.setFont(new Font("D2Coding", Font.PLAIN, 13));
        noteField.setBounds(290, 367, 420, 80);
        spendPanel.add(noteField);
        noteField.setColumns(10);

        btnBack = new JButton(prevIcon);
        btnBack.setBounds(662, 506, 48, 48);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                parent.setVisible(true);
            }
        });
        spendPanel.add(btnBack);

        btnUpdate = new JButton(checkIcon);
        btnUpdate.setBounds(735, 506, 48, 48);
        // btnAdd에 ActionListener 추가
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int newAmount = Integer.parseInt(paymentamount.getText().trim());
                    String newCategory = (String) category.getSelectedItem();
                    String newNotes = noteField.getText().trim();

                    id = transaction.getId();
                    transaction.setType("지출");
                    transaction.setAmount(newAmount);
                    transaction.setCategory(newCategory);
                    transaction.setNotes(newNotes);

                    dao.update(transaction, id);

                    // 성공 메시지
                    JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
                    dispose(); // 창 닫기
                    parent.setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "금액은 숫자로 입력해야 합니다.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "업데이트 중 오류가 발생했습니다.");
                    ex.printStackTrace();
                }
                app.UpdateSuccess();

            }
        });
        spendPanel.add(btnUpdate);

        // 수입 탭
        incomePanel = new JPanel();
        incomePanel.setLayout(null);
        tabbedPane.addTab("수입", incomePanel);

        lblDate_1 = new JLabel("");
        lblDate_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate_1.setBounds(252, 10, 300, 80);
        incomePanel.add(lblDate_1);

        income = new JTextField();
        income.setFont(new Font("D2Coding", Font.PLAIN, 13));
        income.setColumns(10);
        income.setBounds(290, 129, 420, 80);
        incomePanel.add(income);

        category_1 = new JComboBox<>();
        category_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        category_1.setBounds(122, 248, 560, 80);
        final DefaultComboBoxModel<String> incomeModel = new DefaultComboBoxModel<String>(INCOME_CATEGORIES);
        category_1.setModel(incomeModel);
        incomePanel.add(category_1);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        textField_1.setColumns(10);
        textField_1.setBounds(290, 368, 420, 80);
        incomePanel.add(textField_1);

        lblMemo_1 = new JLabel("Memo");
        lblMemo_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblMemo_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblMemo_1.setBounds(95, 367, 100, 80);
        incomePanel.add(lblMemo_1);

        btnBack_1 = new JButton(prevIcon);
        btnBack_1.setBounds(662, 506, 48, 48);
        btnBack_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                parent.setVisible(true);
            }
        });
        incomePanel.add(btnBack_1);

        btnUpdate_1 = new JButton(checkIcon);
        btnUpdate_1.setBounds(735, 506, 48, 48);
        incomePanel.add(btnUpdate_1);
        btnUpdate_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // 입력 데이터 추출 및 검증
                    int newAmount = Integer.parseInt(income.getText().trim());
                    String newCategory = (String) category_1.getSelectedItem();
                    String newNotes = textField_1.getText().trim();

                    id = transaction.getId();
                    transaction.setType("수입");
                    transaction.setAmount(newAmount);
                    transaction.setCategory(newCategory);
                    transaction.setNotes(newNotes);
                    dao.update(transaction, id);

                    // 성공 메시지
                    JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
                    dispose(); // 창 닫기
                    parent.setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "금액은 숫자로 입력해야 합니다.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "업데이트 중 오류가 발생했습니다.");
                    ex.printStackTrace();
                }
                app.UpdateSuccess();

            }
        });
        btnBack_1.setBorderPainted(false);
        btnBack_1.setContentAreaFilled(false);
        btnBack_1.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setFocusPainted(false);

        lblAmount_1 = new JLabel("Amonut");
        lblAmount_1.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblAmount_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmount_1.setBounds(95, 129, 100, 80);
        spendPanel.add(lblAmount_1);
        btnUpdate_1.setBorderPainted(false);
        btnUpdate_1.setContentAreaFilled(false);
        btnUpdate_1.setFocusPainted(false);

        JLabel lblAmount = new JLabel("Amonut");
        lblAmount.setFont(new Font("D2Coding", Font.PLAIN, 13));
        lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmount.setBounds(95, 129, 100, 80);
        incomePanel.add(lblAmount);
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(sdf.format(date));
        lblDate_1.setText(sdf.format(date));
    }
}
