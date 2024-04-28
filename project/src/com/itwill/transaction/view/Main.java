package com.itwill.transaction.view;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
    private JFrame frame;

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
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Transaction Management");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(null);
        frame.setResizable(false);

        
    }
}
