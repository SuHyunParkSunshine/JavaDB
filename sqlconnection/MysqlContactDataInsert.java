package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class MysqlContactDataInsert {

	private Connection connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "suhyun", "1234");
			System.out.println("데이터베이스가 연결되었습니다.");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void insertContactWithStatement(Connection con) {
		Statement st = null;
		String[] cates = { "friend", "family", "colleague", "etc" };
		Random rd = new Random();
		int totcnt = 1000000;

		try {
			st = con.createStatement();

			for (int i = 1; i <= totcnt; i++) {
				String cname = "name" + i;
				String category = cates[rd.nextInt(4)];
				String address = "addr" + i;
				String work = "company" + i;
				String birth = String.format("%4d-%02d-%02d", rd.nextInt(1950, 2022), rd.nextInt(1, 13),
						rd.nextInt(1, 29));

				String sql = String.format(
						"insert into contact (cid, cname, category, address, work, birth) "
								+ "values (%d, '%s', '%s', '%s', '%s', '%s')",
						i, cname, category, address, work, birth);
				System.out.println(String.format("%.2f:%d/%d", i * 100 / (double) totcnt, i, totcnt));

				st.executeUpdate(sql);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void closeDB(Connection con) {
		try {
			con.close();
			System.out.println("데이터베이스가 닫혔습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MysqlContactDataInsert di = new MysqlContactDataInsert();

		Connection con = di.connectDB();
		if (con != null) {
			di.insertContactWithStatement(con);
			di.closeDB(con);
		}

	}

}
