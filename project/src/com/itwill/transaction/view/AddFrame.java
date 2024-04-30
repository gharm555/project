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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Component parent;
    private TransactionDao dao = TransactionDao.getInstance();
    private JTextField textField_1;
    private JLabel lblDate;
    private JComboBox category;
    private JLabel lblNote;
    private JButton btnBack;
    private JButton btnAdd;
    private JLabel lblDate_1;
    private JTextField income;
    private JComboBox category_1;
    private JLabel lblMemo_1;
    private JButton noteField_1;
    private JButton btnAdd_1;
    private JTextField paymentamount;
    private JTextField noteField;

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

    public AddFrame(Component parent) {
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

        lblNote = new JLabel("Memo");
        lblNote.setHorizontalAlignment(SwingConstants.CENTER);
        lblNote.setBounds(184, 237, 104, 62);
        spendPanel.add(lblNote);

        noteField = new JTextField();
        noteField.setBounds(351, 237, 168, 62);
        spendPanel.add(noteField);
        noteField.setColumns(10);

        btnBack = new JButton("<");
        btnBack.setBounds(402, 393, 117, 29);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        spendPanel.add(btnBack);

        btnAdd = new JButton("v");
        btnAdd.setBounds(537, 393, 117, 29);
        // btnAdd에 ActionListener 추가
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 입력 데이터 추출
                String amountText = paymentamount.getText().trim();
                String categorySelected = (String) category.getSelectedItem();
                String noteText = noteField.getText().trim();

                // 데이터 검증
                if (amountText.isEmpty() || categorySelected == null || noteText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "모든 필드를 채워주세요.");
                    return;
                }

                // 데이터 파싱 (금액을 double 형으로 변환)
                double amount;
                try {
                    amount = Double.parseDouble(amountText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "금액은 숫자로 입력해야 합니다.");
                    return;
                }

                // 데이터베이스에 저장
                try {
                    Transaction transaction = new Transaction();
                    transaction.setAmount(amount);
                    transaction.setCategory(categorySelected);
                    transaction.setNotes(noteText);
                    transaction.setDate(new Date()); // 현재 날짜 사용, 필요에 따라 다른 날짜 지정 가능

                    dao.create(transaction); // TransactionDao의 create 메소드를 호출하여 데이터 저장
                    JOptionPane.showMessageDialog(null, "지출이 성공적으로 추가되었습니다.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "데이터 저장 중 오류가 발생했습니다.");
                    ex.printStackTrace();
                }
            }
        });
        spendPanel.add(btnAdd);

        // 수입 탭
        JPanel incomePanel = new JPanel();
        incomePanel.setLayout(null);
        tabbedPane.addTab("수입", incomePanel);

        lblDate_1 = new JLabel("");
        lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate_1.setBounds(269, 29, 122, 38);
        incomePanel.add(lblDate_1);

        income = new JTextField();
        income.setColumns(10);
        income.setBounds(184, 79, 291, 72);
        incomePanel.add(income);
        
                noteField_1 = new JButton("<");
                noteField_1.setBounds(402, 393, 117, 29);
                noteField_1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                incomePanel.add(noteField_1);

        category_1 = new JComboBox();
        category_1.setBounds(141, 163, 378, 62);
        incomePanel.add(category_1);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(351, 237, 168, 62);
        incomePanel.add(textField_1);
        
                lblMemo_1 = new JLabel("Memo");
                lblMemo_1.setHorizontalAlignment(SwingConstants.CENTER);
                lblMemo_1.setBounds(184, 237, 104, 62);
                incomePanel.add(lblMemo_1);

        btnAdd_1 = new JButton("v");
        btnAdd_1.setBounds(537, 393, 117, 29);
        incomePanel.add(btnAdd_1);

    }

    public void updateDateLabel(Date date) {
        lblDate.setText(String.format("%1$tY-%1$tm-%1$td", date));
    }
}
