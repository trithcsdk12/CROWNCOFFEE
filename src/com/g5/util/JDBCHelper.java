/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCHelper {

    public static final Properties props = JDBCHelper.loadDbProperties();
    private static Connection connection = null;

//    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    private static String dburl = "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true;database=Polypro";
//    private static String username = "sa";
//    private static String password = "123";
//;encrypt=true;trustServerCertificate=true
    // 1433

    /*
* Nạp driver
     */
    static {
        String driver = props.getProperty("driver");
        try {
            Class.forName(driver);
            connection = openConnection();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Connection openConnection() {
        Connection con = null;
        String url = props.getProperty("url")+props.getProperty("dtbname");
        String user = props.getProperty("user");
        String pass = props.getProperty("pass");
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
        }

        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Properties loadDbProperties() {
        try {
            Properties props = new Properties();
            props.load(JDBCHelper.class.getResource("Jdbc.properties").openStream());
            return props;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
        public static Properties loadTTProperties() {
        try {
            Properties props = new Properties();
            props.load(JDBCHelper.class.getResource("ThongTin.properties").openStream());
            return props;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    /**
//     * Xây dựng PreparedStatement
//     *
//     * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời
//     * gọi thủ tục lưu
//     * @param args là danh sách các giá trị được cung cấp cho các tham số trong
//     * câu lệnh sql
//     * @return PreparedStatement tạo được
//     * @throws java.sql.SQLException lỗi sai cú pháp
//     */
    public static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        
        Connection connection = openConnection();
        PreparedStatement pstmt = null;
        
        if (sql.trim().contains("{")) {
            pstmt = connection.prepareCall(sql);
        } else {
            pstmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

//    /**
//     * Thực hiện câu lệnh SQL thao tác (INSERT, UPDATE, DELETE) hoặc thủ tục lưu
//     * thao tác dữ liệu
//     *
//     * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời
//     * gọi thủ tục lưu
//     * @param args là danh sách các giá trị được cung cấp cho các tham số trong
//     * câu lệnh sql *
//     */
    public static void executeUpdate(String sql, Object... args) {
        try {

            PreparedStatement stmt = prepareStatement(sql, args);
            try {
                stmt.executeUpdate();
            } finally {
                closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    /**
//     * Thực hiện câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ
//     * liệu
//     *
//     * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời
//     * gọi thủ tục lưu
//     * @param args là danh sách các giá trị được cung cấp cho các tham số trong
//     * câu lệnh sql
//     */
    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        Connection c = connection;

        //    System.out.println(props.getProperty("url"));
    }
}
