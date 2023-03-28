FROM openjdk:11-jdk

# 작업 디렉토리를 /app으로 설정합니다.
WORKDIR /app

# builds/lib 디렉토리에 있는 .jar 파일을 /app으로 복사합니다.
COPY "build/libs/somojeon-0.0.1-SNAPSHOT.jar" /app/

## 컨테이너에서 실행될 명령어를 지정합니다.
#CMD ["java", "-jar", "/app/somojeon-0.0.1-SNAPSHOT.jar"]