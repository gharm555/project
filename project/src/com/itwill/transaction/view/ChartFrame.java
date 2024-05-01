package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import java.util.Calendar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.itwill.transaction.controller.TransactionDao;

public class ChartFrame extends JFrame {
	public interface ChartNotify {
		void chartshow();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel spendPanel;
	private JPanel incomePanel;
	private Component parent;
	private ChartNotify app;
	private TransactionDao dao;
	private JButton prevMonthButton;
	private JButton nextMonthButton;
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	private int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
	private JComboBox<String> dataLoadTypeComboBox;

	/**
	 * Launch the application.
	 */
	public static void showChartFrame(Component parent, ChartNotify app, TransactionDao dao) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChartFrame frame = new ChartFrame(parent, app, dao);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChartFrame(Component parent, ChartNotify app, TransactionDao dao) {
		this.parent = parent;
		this.app = app;
		this.dao = dao;
		init();
		loadChartDataBYMonth(currentYear, currentMonth);

		prevMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentMonth == 1) {
					currentMonth = 12;
					currentYear--;
				} else {
					currentMonth--;
				}
				loadChartDataBYMonth(currentYear, currentMonth);
			}
		});

		nextMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentMonth == 12) {
					currentMonth = 1;
					currentYear++;
				} else {
					currentMonth++;
				}
				loadChartDataBYMonth(currentYear, currentMonth);
			}
		});
		dataLoadTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedOption = (String) dataLoadTypeComboBox.getSelectedItem();
				if (selectedOption.equals("월별")) {
					loadChartDataBYMonth(currentYear, currentMonth);
				} else if (selectedOption.equals("연도별")) {
					loadChartDataByYear(currentYear);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(12, 10, 810, 541);
		contentPane.add(tabbedPane);

		spendPanel = new JPanel();
		tabbedPane.addTab("지출", spendPanel);
		spendPanel.setLayout(null);
		spendPanel.setLayout(new BorderLayout());

		incomePanel = new JPanel();
		tabbedPane.addTab("수입", incomePanel);
		incomePanel.setLayout(null);
		incomePanel.setLayout(new BorderLayout());

		prevMonthButton = new JButton("<");
		prevMonthButton.setBounds(10, 560, 50, 30); // 위치와 크기 설정
		contentPane.add(prevMonthButton);

		nextMonthButton = new JButton(">");
		nextMonthButton.setBounds(780, 560, 50, 30); // 위치와 크기 설정
		contentPane.add(nextMonthButton);

		String[] dataLoadOptions = { "월별", "연도별" };
		dataLoadTypeComboBox = new JComboBox<>(dataLoadOptions);
		dataLoadTypeComboBox.setBounds(300, 560, 150, 30); // 위치와 크기 설정
		contentPane.add(dataLoadTypeComboBox);

	}

	private void loadChartDataBYMonth(int year, int month) {
		Map<String, Integer> spendData = dao.getSumByCategoryAndMonth("지출", year, month);
		DefaultPieDataset spendDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : spendData.entrySet()) {
			spendDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(spendPanel, spendDataset, "지출 차트 - " + year + "년 " + month + "월");

		Map<String, Integer> incomeData = dao.getSumByCategoryAndMonth("수입", year, month);
		DefaultPieDataset incomeDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : incomeData.entrySet()) {
			incomeDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(incomePanel, incomeDataset, "수입 차트 - " + year + "년 " + month + "월");
	}

	private void loadChartDataByYear(int year) {
		Map<String, Integer> spendData = dao.getSumByCategoryAndYear("지출", year);
		DefaultPieDataset spendDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : spendData.entrySet()) {
			spendDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(spendPanel, spendDataset, "지출 차트 - " + year + "년");

		Map<String, Integer> incomeData = dao.getSumByCategoryAndYear("수입", year);
		DefaultPieDataset incomeDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : incomeData.entrySet()) {
			incomeDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(incomePanel, incomeDataset, "수입 차트 - " + year + "년");
	}

	private void updateChart(JPanel panel, DefaultPieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		panel.removeAll();

		panel.add(chartPanel, BorderLayout.CENTER);
		panel.validate();
	}
}
