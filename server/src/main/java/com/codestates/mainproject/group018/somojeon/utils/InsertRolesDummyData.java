package com.codestates.mainproject.group018.somojeon.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRolesDummyData {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String id = System.getenv("RDS_MYSQL_ADMIN_ID"); //root

        String password = System.getenv("RDS_MYSQL_ADMIN_PASSWORD"); //password!

        String url = "jdbc:mysql://"+System.getenv("AWS_RDS_ENDPOINT") + "/stackoverflow";




        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 패스워드 인코더 생성

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성
            for (int i = 14 +1; i <= 14 + 100; i++) {
                int voteCount = (int) (Math.random() * 20 + 1) + 30; // 투표 수 (평균 30, 표준편차 5인 정규분포)

                // 패스워드 인코딩

                // INSERT 문 생성
                String sql = "INSERT INTO member_roles (member_member_id, roles) VALUES (?, ?)";
//                String sql = "DELETE FROM member_roles WHERE member_member_id = 15 LIMIT 1";

                pstmt = conn.prepareStatement(sql);

                // INSERT 문 파라미터 설정
                pstmt.setInt(1, i);
                pstmt.setString(2, "USER");

                // INSERT 문 실행
                pstmt.executeUpdate();
            }

            System.out.println("100 rows inserted successfully.");

        } catch (SQLException | ClassNotFoundException e) {
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
