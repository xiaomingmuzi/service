package utls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SaveToDB {

	public static void saveToDB(String name,String password,String number) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");//加载MySQL JDBC驱动程序
//			System.out.println("Success loading MySql Driver");
			
			Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT","root","111111");
			if(!connect.isClosed()) {
//			System.out.println("连接数据库成功");
			Statement statement= connect.createStatement();
			
			String inserData="insert into student values ('"+name+"',"+password+","+number+",'男')";
			statement.execute(inserData);
			
			String sql="select * from student";
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()) {
				System.out.print("姓名："+rs.getString("name")+"\t");
				System.out.print("学号："+rs.getString("number")+"\t");
				System.out.print("年龄："+rs.getString("age")+"\t");
				System.out.println("性别："+rs.getString("sex")+"\t");
			}
			System.out.println("*********************\n\n\n");
		
			rs.close();
			connect.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Fail load!!!");
		}
	}
}
