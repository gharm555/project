package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.itwill.transaction.controller.TransactionDao;
import com.itwill.transaction.model.Transaction;
import com.itwill.transaction.view.AddFrame.AddNotify;
import com.itwill.transaction.view.ChartFrame.ChartNotify;
import com.itwill.transaction.view.UpdateFrame.UpdateNotify;
import com.toedter.calendar.JCalendar;

public class Main implements AddNotify, UpdateNotify, ChartNotify {
    private static final String[] COLUMN_NAMES = { "ID", "카테고리", "종류", "금액", "메모" };

    private JFrame frame;
    private JCalendar calendar;
    private JButton createButton;
    private JPanel detailsPanel;
    private Date selectedDate;
    private TransactionDao dao = TransactionDao.getInstance();
    private JTable detailsTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private AddFrame addFrame;
    private JButton deleteButton;
    private JButton chartButton;
    private ImageIcon deleteIcon = new ImageIcon("project/icon/deleteicon.png");
    private ImageIcon chartIcon = new ImageIcon("project/icon/charticon.png");
    private ImageIcon addIcon = new ImageIcon("project/icon/addicon.png");

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
        displayTransactionsForDate(selectedDate);
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
        selectedDate = calendar.getDate();
        calendar.addPropertyChangeListener("calendar", event -> {
            if ("calendar".equals(event.getPropertyName())) {
                selectedDate = calendar.getDate();
                displayTransactionsForDate(selectedDate);
                if (addFrame != null && addFrame.isVisible()) {
                    addFrame.setDate(selectedDate);
                }
            }
        });

        createButton = new JButton(addIcon);
        createButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        createButton.setBounds(750, 370, 70, 70);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFrame.showAddFrame(frame, Main.this, selectedDate);

            }
        });
        frame.getContentPane().add(createButton);

        deleteButton = new JButton(deleteIcon);
        deleteButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        deleteButton.setBounds(750, 452, 70, 70);
        deleteButton.addActionListener(e -> delete());
        frame.getContentPane().add(deleteButton);

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
        detailsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int rowIndex = detailsTable.getSelectedRow();
                    if (rowIndex != -1) {
                        int id = Integer.parseInt(tableModel.getValueAt(rowIndex, 0).toString());
                        Transaction transaction = dao.read(id);
                        UpdateFrame.showUpdateFrame(addFrame, Main.this, transaction, selectedDate);
                    }
                }
            }
        });
        scrollPane.setViewportView(detailsTable);

        chartButton = new JButton(chartIcon);
        chartButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        chartButton.setBounds(12, 10, 70, 70);
        chartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TransactionDao dao = TransactionDao.getInstance();
                ChartFrame.showChartFrame(frame, Main.this, dao);

            }
        });
        frame.getContentPane().add(chartButton);

    }

    private void displayTransactionsForDate(Date date) {
        List<Transaction> transactions = dao.getTransactionsByDate(date);
        resetTableModel(transactions);
    }

    private void resetTableModel(List<Transaction> transaction) {

        tableModel = new DefaultTableModel(null, COLUMN_NAMES) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Transaction t : transaction) {
            Object[] row = { t.getId(), t.getCategory(), t.getType(), t.getAmount(), t.getNotes() };
            tableModel.addRow(row);
        }
        detailsTable.setModel(tableModel);
    }

    public void delete() {
        // 테이블에서 선택된 행의 인덱스
        int index = detailsTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(frame, "행을 선택하세요", "오류", 2);
            return;
        } else {
            int confirm = JOptionPane.showConfirmDialog(frame, "정말 삭제할까요?", "삭제", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                Integer id = (Integer) tableModel.getValueAt(index, 0);
                int result = dao.delete(id);
                if (result == 1) {
                    JOptionPane.showMessageDialog(frame, "삭제 성공");
                    displayTransactionsForDate(selectedDate);
                } else {
                    JOptionPane.showMessageDialog(frame, "삭제 실패", "삭제", 0);
                }
            }
        }

    }

    @Override
    public void addSuccess() {
        displayTransactionsForDate(selectedDate);
    }

    @Override
    public void UpdateSuccess() {
        displayTransactionsForDate(selectedDate);

    }

    @Override
    public void chartshow() {
        // TODO Auto-generated method stub

    }
}
