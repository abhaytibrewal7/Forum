package database;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class databaseConnect {
	private static databaseConnect instance=new databaseConnect();
	
	String url="jdbc:mysql://localhost:3306/forum";
	String user="root";
	String password="";
	
	String driverClass="com.mysql.jdbc.Driver";

	public databaseConnect() {
		try{
			Class.forName(driverClass);
			}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static databaseConnect getInstance(){
		return instance;
	}
	public Connection getConnection(){
		Connection connection=null;
		try{
			connection=DriverManager.getConnection(url,user,password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}	
}
