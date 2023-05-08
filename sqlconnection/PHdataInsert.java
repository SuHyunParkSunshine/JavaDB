package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class PHdataInsert {

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

	private void insertPhoneWithStatement(Connection con) {
		
		String[] types = { "phnum", "hnum", "comnum", "fax", "etc" };
		Random rd = new Random();
		int totcnt = 1000000;
		String sql = "insert into phone (cid, seq, number, type) values (?, ?, ?, ?)";

		
		try {

			PreparedStatement ps = con.prepareStatement(sql);

			for (int i = 1; i <= totcnt; i++) {

				int cid = i;
				int seq = i;
				int idx = rd.nextInt(5);
				String type = types[idx];
				String number;

				if (idx == 0) {
					number = String.format("010-%04d-%04d", rd.nextInt(0, 10000), rd.nextInt(0000, 10000));
				} else {
					number = String.format("%03d-%03d-%04d", rd.nextInt(0, 100), rd.nextInt(0, 1000), rd.nextInt(0, 10000));
				}

				ps.setInt(1, cid);
				ps.setInt(2, seq);
				ps.setString(3, number);
				ps.setString(4, type);

				System.out.println(String.format("%.2f:%d/%d", i * 100 / (double) totcnt, i, totcnt));
				
				ps.executeUpdate();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("데이터베이스가 입력되었습니다.");

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
		PHdataInsert di = new PHdataInsert();

		Connection con = di.connectDB();
		if (con != null) {
			di.insertPhoneWithStatement(con);
			di.closeDB(con);
		}

	}

}
