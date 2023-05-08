package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HsqlInsertData {

	Connection con = null;
	ResultSet rs = null;
	Statement st = null;

	private void selectData() {
		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/telephone", "suhyun", "1234");

			st = con.createStatement();
			rs = st.executeQuery("Select dno, dname, budget from dept");

			while (rs.next()) {
				System.out.println(
						String.format("%s. %s, %d", rs.getString("dno"), rs.getString("dname"), rs.getInt("budget")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

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
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/telephone", "suhyun", "1234");
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
		HsqlInsertData tt = new HsqlInsertData();

		if (tt.connectDB()) {
			tt.selectData();

			tt.closeDB();
		}

//		for (int i = 0; i < 100; i++) {
//			if (tt.connectDB()) {
//				tt.insertDeptStatement("d" + i, "dname" + i, 36126 + (i * 100));
//
//				tt.closeDB();
//
//			}
//
//		}

	}
}
