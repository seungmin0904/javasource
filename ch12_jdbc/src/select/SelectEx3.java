package select;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectEx3 {
    public static void main(String[] args) {
        // 드라이버 로드
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;  // select 문 결과 닫는 객체

        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("oracle.jdbc.OracleDriver");

                // connection 객체 생성
                // jdbc:oracle:thin:@ -> Oracle 연결문자열
                // localhost 내 컴퓨터 ㅡ 1521 오라클 서버 포트번호
                // xe - 오라클 처음이름
                String url ="jdbc:oracle:thin:@localhost:1521:xe";
                String user ="scott";
                String password = "tiger";
                 con = DriverManager.getConnection(url, user, password);


                if(con!=null){
                    //System.out.println("연결되었습니다.");
                    
                    // 입력 받아서 조회하기
                    System.out.print("조회할 사원번호 입력 :");
                    int empno = Integer.parseInt(sc.nextLine());

                    // emp 테이블 : empno =7369 조회
                    // String sql = "select * from emp where empno=" + empno; // 외부 공격이 들어올 수 있기때문에
                    String sql = "select * from emp where empno=?"; // ?를 써서 사용



                    // Statement 객체 생성
                    pstmt = con.prepareStatement(sql);
                    // 첫번째 물음표(입력값)에 가져오는 empno(데이터) 값을 셋팅해줘
                    pstmt.setInt(1, empno);
                    // Query 수행 
                    rs = pstmt.executeQuery();
                    // ResultSet 객체로부터 데이터 추출
                    while (rs.next()) {
                        // 베이스 타입이 number -> int로 가져옴
                        empno = rs.getInt(1);
                        // varchar -> String 
                        String ename = rs.getString("ename");
                        String job = rs.getString("job");
                        int mgr = rs.getInt("mgr");
                        String hireDate = rs.getString("hireDate");
                        int sal = rs.getInt("sal");
                        int comm = rs.getInt("comm");
                        int deptno = rs.getInt("deptno");

                        System.out.printf("%d %s %s %d %s %d %d %d\n", empno,ename,job,mgr,hireDate,sal,comm,deptno);
                    }




                }

        } catch (Exception e) {
            
            e.printStackTrace();
        } finally{
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
