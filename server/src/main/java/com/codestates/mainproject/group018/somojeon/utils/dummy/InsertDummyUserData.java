package com.codestates.mainproject.group018.somojeon.utils.dummy;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

class InsertDummyUserData {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        insertUserDummy();
    }

    static void insertUserDummy() throws ClassNotFoundException, SQLException {

        String id = System.getenv("RDS_ID");
        String password = System.getenv("RDS_PASSWORD");
        String url = "jdbc:mysql://" + System.getenv("AWS_RDS_ENDPOINT") + "/somojeon?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8&useSSL=true";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 패스워드 인코더 생성
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성
            for (int i =  1; i <= 100; i++) {
                // INSERT 문 생성
                String sql = "INSERT INTO user (user_id, email, nick_name, password, user_status, created_at, modified_at)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?)";

                pstmt = conn.prepareStatement(sql);
                // INSERT 문 파라미터 설정
                pstmt.setLong(1, i);
                pstmt.setString(2, "user"+i+"@dummy.com");
                pstmt.setString(3, "nick"+i);
                String encodedPassword = "{bcrypt}"+passwordEncoder.encode("password" + i);
                pstmt.setString(4, encodedPassword);
                User.UserStatus[] statuses = {User.UserStatus.USER_ACTIVE, User.UserStatus.USER_QUIT, User.UserStatus.USER_SLEEP, User.UserStatus.USER_NEW};
                int randomIndex = new Random().nextInt(statuses.length);
                pstmt.setString(5, statuses[randomIndex].getStatus());
                pstmt.setObject(6, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                pstmt.setObject(7, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                // INSERT 문 실행
                pstmt.executeUpdate();
                System.out.println("100 rows inserted successfully.");

            }

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

