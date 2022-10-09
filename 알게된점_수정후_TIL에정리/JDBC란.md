# what is JDBC

Java Database Connectivity

자바에서 DB 프로그래밍을 하기 위해 사용되는 API 이다. 어떤 DB가 오더라도 연결시켜줄수 있다.(DI, IOC로 인해 동작가능)

> JDBC API 사용 APP의 기본 구성

<img src = "https://github.com/steadykyu/makeBoardProject/blob/master/%EC%95%8C%EA%B2%8C%EB%90%9C%EC%A0%90_%EC%88%98%EC%A0%95%ED%9B%84_TIL%EC%97%90%EC%A0%95%EB%A6%AC/img/jdbc_1.png">

# JDBC 프로그래밍 동작 흐름

1. JDBC 드라이버 로드
2. DB 연결
3. DB에서 SQL문 작성 및 동작
4. DB 연결 종료

## JDBC 드라이버

DBMS와 통신을 담당하는 자바 클래스

DBMS 종료에 따라 알맞은 JDBC 드라이버가 필요하다.

- 마치 우리가 StringBuffer 클래스를 가져와서 값과 메서드를 사용하듯이, JDBC 드라이버도 비슷한 형식으로 구성되어있다.
- 종류

```
Class.forName("JDBC드라이버 이름")
MYSQL : com.mysql.jdbc.Driver
오라클 : oracle.jdbc.driver.Driver
MSSQL : com.microsoft.sqlserver.jdbc.SQLserverDriver
```

## JDBC URL

- DBMS와의 연결을 위한 식별 값
- JDBC드라이버에 따라 형식이 다름
- 종류

```
JDBC:[DBMS]:[데이터베이스식별자]

MYSQL : jdbc:mysql://IP:3306/DBNAME?characterEncoding=utf8&autoReconnect=true

MSSQL : jdbc:sqlserver://IP:1433;DatabaseName=DBNAME

ORACEL : jdbc:oracle:thin:@IP:1521:DBNAME
```

# 데이터 조회를 위한 주요 메서드

- getString()
- getInt(), getLong(), getFloat(), getDouble()
- getTimestamp(), getDate(), getTime()

# 출처

https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/

https://dyjung.tistory.com/50
