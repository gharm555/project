package com.itwill.transaction.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

//InformationDialog.java
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InformationDialog extends JDialog {
 private JTextArea informationTextArea;

 public InformationDialog(Frame owner) {
     super(owner, "Transaction Information", true);
     setSize(400, 300);
     setLocationRelativeTo(owner);
     informationTextArea = new JTextArea(10, 25);
     informationTextArea.setEditable(false);
     JScrollPane scrollPane = new JScrollPane(informationTextArea);
     scrollPane.setPreferredSize(new Dimension(380, 280));
     add(scrollPane, BorderLayout.CENTER);
     add(new JLabel("Details:"), BorderLayout.NORTH);
 }

 public void displayInformation(String info) {
     informationTextArea.setText(info);
     setVisible(true);
 }
}

