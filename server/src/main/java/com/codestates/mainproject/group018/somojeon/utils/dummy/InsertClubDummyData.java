package com.codestates.mainproject.group018.somojeon.utils.dummy;

import com.codestates.mainproject.group018.somojeon.club.enums.ClubStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Profile("dummy-insert")
@Component
public class InsertClubDummyData {

    @Value("${defaultClub.image.address}")
    static String defaultClubImage;
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
                ClubStatus clubStatus = ClubStatus.CLUB_ACTIVE;
                String categoryName = "카테고리";
                String content = "클럽 소개 내용입니다" + i; // 질문 내용
                String local = "서울특별시 강동구";
                boolean isSecret = false;
                int viewCount = (int) (Math.random() * 10 + 1) + 10; // 조회수
                int memberCount = (int) (Math.random() * 10 + 1) + 10; // 멤버수
                LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
                LocalDateTime modifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
                String clubImageUrl = defaultClubImage;

                // INSERT 문 생성
                String sql = "INSERT INTO question (club_name, club_status, category_name, content, local, is_secret, view_count, member_count, created_at, modified_at, club_image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);

                // INSERT 문 파라미터 설정
                pstmt.setString(1, clubName);
                pstmt.setString(2, String.valueOf(clubStatus));
                pstmt.setString(3, categoryName);
                pstmt.setString(4, content);
                pstmt.setString(5, local);
                pstmt.setBoolean(6, isSecret);
                pstmt.setInt(7, viewCount);
                pstmt.setInt(8, memberCount);
                pstmt.setObject(9, createdAt);// created_at
                pstmt.setObject(10, modifiedAt);
                pstmt.setString(11, clubImageUrl);

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
