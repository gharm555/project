package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

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
	private JComboBox<String> viewModeComboBox;
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
		loadChartData("월별");
	}
	 private void initComboBox() {
	        String[] viewModes = {"월별", "연도별"};
	        viewModeComboBox = new JComboBox<>(viewModes);
	        viewModeComboBox.setBounds(12, 10, 150, 30); // 적절한 위치 설정 필요
	        contentPane.add(viewModeComboBox);

	        viewModeComboBox.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String selectedMode = (String) viewModeComboBox.getSelectedItem();
	                loadChartData(selectedMode);
	            }
	        });
	    }

	    private void loadChartData(String mode) {
	        if (mode.equals("연도별")) {
	            // 연도별 데이터 로드 로직
	            Map<String, Integer> data = dao.getSumByYear();
	            DefaultPieDataset dataset = new DefaultPieDataset();
	            data.forEach((year, total) -> dataset.setValue(year + "년", total));
	            updateChart(spendPanel, dataset, "지출 차트 - 연도별");
	        } else {
	            // 월별 데이터 로드 로직
	            Map<String, Integer> data = dao.getSumByMonth();
	            DefaultPieDataset dataset = new DefaultPieDataset();
	            data.forEach((month, total) -> dataset.setValue(month + "월", total));
	            updateChart(spendPanel, dataset, "지출 차트 - 월별");
	        }
	    }
	
	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 600);
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

	}

//	private void loadChartData() {
//		Map<String, Integer> spendData = dao.getSumByCategory("지출");
//		DefaultPieDataset spendDataset = new DefaultPieDataset();
//		for (Map.Entry<String, Integer> entry : spendData.entrySet()) {
//			spendDataset.setValue(entry.getKey(), entry.getValue());
//		}
//		updateChart(spendPanel, spendDataset, "지출 차트");
//
//		Map<String, Integer> incomeData = dao.getSumByCategory("수입");
//		DefaultPieDataset incomeDataset = new DefaultPieDataset();
//		for (Map.Entry<String, Integer> entry : incomeData.entrySet()) {
//			incomeDataset.setValue(entry.getKey(), entry.getValue());
//		}
//		updateChart(incomePanel, incomeDataset, "수입 차트");
//	}

	private void updateChart(JPanel panel, DefaultPieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		panel.removeAll();

		
		panel.add(chartPanel, BorderLayout.CENTER);
		panel.validate();
	}
}
