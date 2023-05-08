package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDataDept {
	Connection con = null;

	//PreparedStatement, executeUpdate
	private void insertDept(String dno, String dname, int budget) {

		String sql = "insert into dept (dno, dname, budget) values (?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dno);// ?의 순서중 첫번째 ?.
			ps.setString(2, dname);
			ps.setInt(3, budget);
			ps.executeUpdate(); // Int를 return함. 즉 업데이트 된 레코드의 갯수를 return함.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("데이터베이스가 입력되었습니다.");

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

	public static void main(String[] args) {
		InsertDataDept tt = new InsertDataDept();
		if (tt.connectDB()) {
			tt.insertDept("d10", "dname", 123);
			tt.closeDB();
		}

	}

}