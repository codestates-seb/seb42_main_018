package com.codestates.mainproject.group018.somojeon.utils.dummy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class InsertQuestionDummyData {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String url = "jdbc:mysql://"+System.getenv("AWS_RDS_ENDPOINT")+"/stackoverflow";
        String id = System.getenv("RDS_MYSQL_ADMIN_ID"); // root
        String password = System.getenv("RDS_MYSQL_ADMIN_PASSWORD"); // password!

//        String url = "jdbc:mysql://localhost:3306/stackoverflow";
//        String id = "root";
//        String password = System.getenv("MYSQL_PASSWORD");

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성
            for (int i = 1; i <= 100; i++) {
                String title = "질문의 제목을 설정해주세요" + i; // 제목
                String content = "질문의 내용을 작성해주세요" + i; // 질문 내용
                int voteCount = (int) (Math.random() * 10 + 1) + 40; // 추천 수
                int views = (int) (Math.random() * 20 + 1) + 10; // 조회 수
                long memberId = 1;

                // INSERT 문 생성
                String sql = "INSERT INTO question (title, content, views, vote_count, created_at, modified_at, member_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);

                // INSERT 문 파라미터 설정
                pstmt.setString(1, title);
                pstmt.setString(2, content);
                pstmt.setInt(3, voteCount);
                pstmt.setInt(4, views);
                pstmt.setObject(5, LocalDateTime.now()); // created_at
                pstmt.setObject(6, LocalDateTime.now()); // modified_at
                pstmt.setLong(7, memberId);

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
