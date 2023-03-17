package com.codestates.mainproject.group018.somojeon.utils.dummy;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

class insertDummyData {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        insertUserDummy();

    }

    static void insertUserDummy() throws ClassNotFoundException, SQLException {

//        String id = System.getenv("RDS_MYSQL_ADMIN_ID"); //root
        String id = "root";
//        String password = System.getenv("RDS_MYSQL_ADMIN_PASSWORD"); //password!
        String password = "pass!";
//        String url = "jdbc:mysql://" + System.getenv("AWS_RDS_ENDPOINT") + "/stackoverflow";
        String url = "jdbc:mysql://localhost:3306/somojeon?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8&useSSL=true";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 패스워드 인코더 생성

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성
            for (int i =  1; i <= 100; i++) {
                int voteCount = (int) (Math.random() * 20 + 1) + 30; // 투표 수 (평균 30, 표준편차 5인 정규분포)

                // 패스워드 인코딩

                // INSERT 문 생성
//                String sql = "INSERT INTO member_roles (member_member_id, roles) VALUES (?, ?)";
                String sql = "INSERT INTO user (user_id, age, created_at, email, gender, modified_at, nick_name, password, user_name, user_status)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//                String sql = "DELETE FROM member_roles WHERE member_member_id = 15 LIMIT 1";

                pstmt = conn.prepareStatement(sql);
                // INSERT 문 파라미터 설정
                pstmt.setLong(1, i);
                int age = (int) (Math.random() * 20 ) + 20;
                pstmt.setInt(2, age);
                pstmt.setObject(3, LocalDateTime.now());
                pstmt.setString(4, "user"+i+"@example.com");
                String[] genders = {"M", "F"};
                int randomIndex = new Random().nextInt(genders.length);
                pstmt.setString(5, genders[randomIndex]);
                pstmt.setObject(6, LocalDateTime.now());
                pstmt.setString(7, "user"+i);
                String encodedPassword = "{bcrypt}"+passwordEncoder.encode("password" + i);
                pstmt.setString(8, encodedPassword);
                pstmt.setString(9, "nick"+i);
                User.UserStatus[] statuses = {User.UserStatus.USER_ACTIVE, User.UserStatus.USER_QUIT, User.UserStatus.USER_SLEEP, User.UserStatus.USER_NEW};
                randomIndex = new Random().nextInt(statuses.length);
                pstmt.setString(10, statuses[randomIndex].getStatus());

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

