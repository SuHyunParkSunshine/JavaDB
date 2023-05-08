package sqlconnection;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Statement : 완결된 Query 문장을 실행할 때
//PreparedStatement : 변수로 값을 추가할 수 있는 Query문을 실행할 때

//select ==> executeQuery : PreparedStatement, Statement
//insert/delete/update ==> executeUpdate : PreparedStatement, Statement 

public class InsertDataDeptsql {
	Connection con = null;

	// Statement, executeUpdate
	private void insertDeptStatement(String dno, String dname, int budget) {

		try {
			Statement st = con.createStatement();

			int cnt = st.executeUpdate(String.format(String
					.format("insert into dept (dno, dname, budget) values ('%s', '%s', '%d')", dno, dname, budget)));
			System.out.println("데이터베이스가 입력되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private boolean connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "suhyun", "1234");
			System.out.println("데이터베이스가 연결되었습니다.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void closeDB() {
		try {
			con.close();
			System.out.println("데이터베이스가 닫혔습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteDepttriggerPrepared(int from, int to) {
		
		String sql = "delete from depttrigger where ? <= id and id <= ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, from);
			ps.setInt(2, to);
			ps.executeUpdate(); // Int를 return함. 즉 업데이트 된 레코드의 갯수를 return함.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("데이터베이스가 삭제되었습니다.");

	}

	private void deleteDepttriggerStatement(int from, int to) {
		
	      try {
	          Statement st = con.createStatement();
	          int cnt = st.executeUpdate(
	                String.format(String.format("delete from depttrigger where %d <= id and id <= %d", from, to)));

	          System.out.println("데이터가" + cnt + "개가 삭제되었습니다.");
	       } catch (SQLException e) {
	          e.printStackTrace();
	       }

	}
	
	private void updateDeptPrepared(String dno, String dname, int budget) {
		try {
			PreparedStatement ps = con.prepareStatement("update dept set dname = ?, budget = ? where dno = ?");
			
			ps.setString(1, dname);
			ps.setInt(2, budget);
			ps.setString(3, dno);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateDeptStatement(String dno, String dname, int budget) {
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(String.format(String.format("update dept set dname = '%s', budget = %d where dno = '%s'", dname, budget, dno)));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		InsertDataDeptsql tt = new InsertDataDeptsql();
		if (tt.connectDB()) {
//			tt.insertDeptStatement("d21", "dname21", 36126);
			tt.deleteDepttriggerStatement(1, 3);
			tt.deleteDepttriggerPrepared(4, 5);
			
//			tt.updateDeptPrepared("d1", "dname1", 1000);
			tt.updateDeptStatement("d10", "dname11", 1230);
			tt.closeDB();
		}

	}

}