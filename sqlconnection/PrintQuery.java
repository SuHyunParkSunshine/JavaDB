package sqlconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

class QueryExe {
	int num;
	String text;
	
	QueryExe(int num, String text) {
		this.num = num;
		this.text = text;
	}
	
	int getNum() { return num; }
	String getText() { return text; }
}

public class PrintQuery {
	
	public void connectDB();
	public void closeDB();
	
	public static void exe01() {
		
		Connection con = connectDB();
		Statement st = con.createStatement();
		String sql;
		ResultSet rs = st.executeQuery(sql);
		
		System.out.println("이것은 Query");
		System.out.println("이것은 실행 결과");
		System.out.println("-".repeat(80));
		
		
		closeDB(con);
		
	}
	
	public static void main(String[] args) {
		
		ArrayList<QueryExe> list = new ArrayList<>();
		
		list.add(new QueryExe(1, "London에 있는 프로젝트 이름을 찾아라"))
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			
			for(QueryExe qe : list) {
				System.out.println(String.format("%d. %s", qe.getNum(), qe.getText()));
			}
			System.out.print("선택<0:exit>: ");
			int sel = sc.nextInt();
			if(sel == 0) break;
			
			switch(sel) {
			case 1 : exe01(); break;
			default : System.out.println("제대로 선택하도록....");
			}
			
		}
		System.out.println("종료합니다.");
	}

	
}
