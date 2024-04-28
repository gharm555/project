package com.itwill.transaction.view;

import static com.itwill.transaction.Jdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;

import com.itwill.transaction.controller.TransactionController;
import com.itwill.transaction.controller.TransactionDao;
import com.itwill.transaction.controller.TransactionDaoImpl;

public class Main {
    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // MVC 컴포넌트 초기화
            TransactionDao transactionDao = new TransactionDaoImpl(connection);
            TransactionView view = new TransactionView();
            TransactionController controller = new TransactionController(transactionDao, view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
