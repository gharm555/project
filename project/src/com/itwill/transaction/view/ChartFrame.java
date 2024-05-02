package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
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
	private JButton closeButton;
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	private int maxYear = Calendar.getInstance().get(Calendar.YEAR) + 5;
	private int minYear = Calendar.getInstance().get(Calendar.YEAR) - 5;
	private int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
	private JComboBox<String> dataLoadTypeComboBox;
	private ImageIcon prevIcon = new ImageIcon("project/icon/previcon.png");
	private ImageIcon nextIcon = new ImageIcon("project/icon/nexticon.png");
	private ImageIcon closeIcon = new ImageIcon("project/icon/closeicon.png");

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
				if (dataLoadTypeComboBox.getSelectedItem().equals("월별")) {
					if (currentMonth == 1) {
						currentMonth = 12;
						currentYear--;
					} else {
						currentMonth--;
					}
					loadChartDataBYMonth(currentYear, currentMonth);
				} else if (dataLoadTypeComboBox.getSelectedItem().equals("연도별")) {
					currentYear--;
					loadChartDataByYear(currentYear);
				}
			}
		});

		nextMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataLoadTypeComboBox.getSelectedItem().equals("월별")) {
					if (currentMonth == 12) {
						currentMonth = 1;
						currentYear++;
					} else {
						currentMonth++;
					}
					loadChartDataBYMonth(currentYear, currentMonth);
				} else if (dataLoadTypeComboBox.getSelectedItem().equals("연도별")) {
					currentYear++;
					loadChartDataByYear(currentYear);
				}
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(parent);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(20, 60, 810, 496);
		contentPane.add(tabbedPane);

		spendPanel = new JPanel();
		tabbedPane.addTab("지출", spendPanel);
		spendPanel.setLayout(null);
		spendPanel.setLayout(new BorderLayout());

		incomePanel = new JPanel();
		tabbedPane.addTab("수입", incomePanel);
		incomePanel.setLayout(null);
		incomePanel.setLayout(new BorderLayout());

		prevMonthButton = new JButton(prevIcon);
		prevMonthButton.setBounds(251, 6, 48, 48); // 위치와 크기 설정
		contentPane.add(prevMonthButton);

		nextMonthButton = new JButton(nextIcon);
		nextMonthButton.setBounds(550, 6, 48, 48); // 위치와 크기 설정
		contentPane.add(nextMonthButton);

		closeButton = new JButton(closeIcon);
		closeButton.setBounds(782, 568, 48, 48); // 위치와 크기 설정
		closeButton.addActionListener(e -> {
			dispose();
			parent.setVisible(true);
		});
		contentPane.add(closeButton);

		String[] dataLoadOptions = { "월별", "연도별" };
		dataLoadTypeComboBox = new JComboBox<>(dataLoadOptions);
		dataLoadTypeComboBox.setFont(new Font("D2Coding", Font.PLAIN, 13));
		dataLoadTypeComboBox.setBounds(680, 6, 150, 30); // 위치와 크기 설정
		contentPane.add(dataLoadTypeComboBox);

		nextMonthButton.setBorderPainted(false);
		nextMonthButton.setContentAreaFilled(false);
		nextMonthButton.setFocusPainted(false);
		prevMonthButton.setBorderPainted(false);
		prevMonthButton.setContentAreaFilled(false);
		prevMonthButton.setFocusPainted(false);
		closeButton.setBorderPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setFocusPainted(false);

	}

	private void loadChartDataBYMonth(int year, int month) {

		// 연도 범위 확인
		if (year < minYear || year > maxYear) {
			JOptionPane.showMessageDialog(this,
					year + "년 데이터는 조회할 수 없습니다. " + minYear + "년부터 " + maxYear + "년까지의 데이터만 조회 가능합니다.");
			return;
		} // 범위를 벗어나면 함수 종료

		Map<String, Integer> spendData = dao.getSumByCategoryAndMonth("지출", year, month);
		DefaultPieDataset spendDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : spendData.entrySet()) {
			spendDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(spendPanel, spendDataset, year + "년 " + month + "월");

		Map<String, Integer> incomeData = dao.getSumByCategoryAndMonth("수입", year, month);
		DefaultPieDataset incomeDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : incomeData.entrySet()) {
			incomeDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(incomePanel, incomeDataset, year + "년 " + month + "월");
	}

	private void loadChartDataByYear(int year) {

		if (year < minYear || year > maxYear) {
			JOptionPane.showMessageDialog(this,
					year + "년 데이터는 조회할 수 없습니다. " + minYear + "년부터 " + maxYear + "년까지의 데이터만 조회 가능합니다.");
			return;
		}
		Map<String, Integer> spendData = dao.getSumByCategoryAndYear("지출", year);
		DefaultPieDataset spendDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : spendData.entrySet()) {
			spendDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(spendPanel, spendDataset, year + "년");

		Map<String, Integer> incomeData = dao.getSumByCategoryAndYear("수입", year);
		DefaultPieDataset incomeDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : incomeData.entrySet()) {
			incomeDataset.setValue(entry.getKey(), entry.getValue());
		}
		updateChart(incomePanel, incomeDataset, year + "년");
	}

	private void updateChart(JPanel panel, DefaultPieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		panel.removeAll();

		TextTitle chartTitle = chart.getTitle();
		chartTitle.setFont(new Font("D2Coding", Font.PLAIN, 24));

		panel.add(chartPanel, BorderLayout.CENTER);
		panel.validate();
	}
}
