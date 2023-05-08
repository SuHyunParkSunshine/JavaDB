package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class HdataInsert {

	private Connection connectDB() {
		try {
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/telephone", "suhyun", "1234");
			System.out.println("데이터베이스가 연결되었습니다.");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void insertContactWithStatement(Connection con) {
		Statement st = null;
		String[] cates = { "friend", "family", "cowork", "etc" };
		Random rd = new Random();
		int totcnt = 1000000;

		try {
			st = con.createStatement();

			for (int i = 1; i <= totcnt; i++) {
				String name = "name" + i;
				String category = cates[rd.nextInt(4)];
				String address = "addr" + i;
				String work = "company" + i;
				String birthday = String.format("%4d-%02d-%02d", rd.nextInt(1950, 2022), rd.nextInt(1, 13),
						rd.nextInt(1, 29));

				String sql = String.format(
						"insert into contact (cid, name, category, address, work, birthday)"
								+ "values (%d, '%s', '%s', '%s', '%s', '%s')",
						i, name, category, address, work, birthday);
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
		HdataInsert di = new HdataInsert();

		Connection con = di.connectDB();
		if (con != null) {
			di.insertContactWithStatement(con);
			di.closeDB(con);
		}

	}

}
