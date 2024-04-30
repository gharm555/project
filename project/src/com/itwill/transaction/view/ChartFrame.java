package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class ChartFrame extends JFrame {
	public interface ChartNotify{
		void chartshow();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel spendPanel;
	private JPanel incomePanel;
	private Component parent;
	private ChartNotify app;
	/**
	 * Launch the application.
	 */
	public static void showChartFrame(Component parent, ChartNotify app) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChartFrame frame = new ChartFrame(parent,app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ChartFrame(Component parent, ChartNotify app) {
		init();
	}
	
	
	/**
	 * Create the frame.
	 */
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		// 원형 그래프 생성
        DefaultPieDataset spendDataset = new DefaultPieDataset();
        spendDataset.setValue("One", new Double(43.2));
        spendDataset.setValue("Two", new Double(10.0));
        spendDataset.setValue("Three", new Double(27.5));
        spendDataset.setValue("Four", new Double(17.5));
        spendDataset.setValue("Five", new Double(11.0));
        spendDataset.setValue("Six", new Double(19.4));

        JFreeChart spendChart = ChartFactory.createPieChart("Spend Chart Example", spendDataset, true, true, false);

        // 차트를 차트 패널에 추가
        ChartPanel spendChartPanel = new ChartPanel(spendChart);
        spendPanel.setLayout(new BorderLayout());
        spendPanel.add(spendChartPanel, BorderLayout.CENTER);
		
		incomePanel = new JPanel();
		tabbedPane.addTab("수입", incomePanel);
		incomePanel.setLayout(null);
		
		// 수입 원형 그래프 생성
		DefaultPieDataset incomeDataset = new DefaultPieDataset();
		incomeDataset.setValue("Category A", new Double(20.0));
		incomeDataset.setValue("Category B", new Double(30.0));
		incomeDataset.setValue("Category C", new Double(25.0));
		incomeDataset.setValue("Category D", new Double(25.0));

		JFreeChart incomeChart = ChartFactory.createPieChart("Income Chart Example", incomeDataset, true, true, false);

		// 차트를 차트 패널에 추가
		ChartPanel incomeChartPanel = new ChartPanel(incomeChart);
		incomePanel.setLayout(new BorderLayout());
		incomePanel.add(incomeChartPanel, BorderLayout.CENTER);
		
		
	}

}
