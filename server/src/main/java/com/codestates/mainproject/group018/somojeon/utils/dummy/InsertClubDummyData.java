package com.codestates.mainproject.group018.somojeon.utils.dummy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Slf4j
@Profile("dummy-insert")
@Component
public class InsertClubDummyData {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String url = "jdbc:mysql://"+System.getenv("RDS_ENDPOINT_DUMMY")+"/somojeon";
        String id = System.getenv("RDS_ID"); // root
        String password = System.getenv("RDS_PASSWORD"); // password!

//        String url = "jdbc:mysql://localhost:3306/stackoverflow";
//        String id = "root";
//        String password = System.getenv("MYSQL_PASSWORD");

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성
            for (int i = 1; i <= 100; i++) {
                String clubName = "클럽 이름입니다" + i; // 제목
                String content = "클럽 소개 내용입니다" + i; // 질문 내용
                String local = "서울특별시 강동구";
                String categoryName = "카테고리";
                boolean isSecret = false;
                int viewCount = (int) (Math.random() * 10 + 1) + 10; // 조회수
                int memberCount = (int) (Math.random() * 10 + 1) + 10; // 멤버수

                // INSERT 문 생성
                String sql = "INSERT INTO question (clubName, content, local, categoryName, is_secret, view_count, member_count) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);

                // INSERT 문 파라미터 설정
                pstmt.setString(1, clubName);
                pstmt.setString(2, content);
                pstmt.setString(3, local);
                pstmt.setString(4, categoryName);
                pstmt.setBoolean(5, isSecret);
                pstmt.setInt(6, viewCount);
                pstmt.setInt(7, memberCount);
                pstmt.setObject(8, LocalDateTime.now()); // created_at

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
