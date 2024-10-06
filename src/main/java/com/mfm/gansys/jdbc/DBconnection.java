package com.mfm.gansys.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class DBconnection {


    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.extension.driver}")
    private String driver;

    java.sql.Connection conn = null;
    public void setUp() {

        System.out.println("SETUP JDBC url : "+url);
        System.out.println("SETUP JDBC user : "+user);
        System.out.println("SETUP JDBC passwd : "+password);
        System.out.println("SETUP JDBC driver : "+driver);
        try {
            Class.  forName(this.driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public java.sql.Connection getConnection() {
        return this.conn;
    }

    public void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
