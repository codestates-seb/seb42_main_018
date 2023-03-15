package com.codestates.mainproject.group018.somojeon.utils;

import java.sql.*;

public class MysqlQuertTest {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String id = System.getenv("RDS_MYSQL_ADMIN_ID"); //root

        String password = System.getenv("RDS_MYSQL_ADMIN_PASSWORD"); //password!

        String url = "jdbc:mysql://"+System.getenv("AWS_RDS_ENDPOINT") + "/stackoverflow";



        try {
            // MySQL 데이터베이스에 연결

            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성


            // INSERT 문 생성

            String sql = "SHOW TABLES";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // 조회 결과 처리
            while (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println(tableName);
            }

            System.out.println("100 rows inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
