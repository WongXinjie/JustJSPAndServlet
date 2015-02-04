/*
 * สýพÝิด
 */
package com.bookstores.cons;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	public static Connection getConnection(){
		Connection con=null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, dbname, dbpassword);
		}catch (SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return con;
	}
	
	public static boolean update(String sql, String[] args){
		try{
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			for(int i=0; i < args.length; i++){
				pstmt.setObject(i+1, args[i]);
			}
			int effect_row = 0;
			effect_row = pstmt.executeUpdate(sql);
			pstmt.close();
			con.close();
			return effect_row > 0;
		}catch(SQLException e){
			return false;
		}
	}
	
	public static int insert(String sql, String[] args){
		try{
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for(int i=0; i < args.length; i++){
				pstmt.setObject(i+1, args[i]);
			}
			pstmt.executeUpdate();
			ResultSet keys = pstmt.getGeneratedKeys();
			if( keys.next()){
				int last_insert_key = keys.getInt(1);
				keys.close();
				pstmt.close();
				con.close();
				return last_insert_key;
			}else{
				return -1;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static boolean delete(String sql, String[] args){
		return update(sql, args);
	}
	
	public static ResultSet query(String sql, String[] args){
		try{
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			for(int i=0; i < args.length; i++){
				pstmt.setObject(i+1, args[i]);
			}
			ResultSet rs = pstmt.executeQuery();
			return rs;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String driver="com.mysql.jdbc.Driver";
	private static final String url ="jdbc:mysql://localhost:3306/bookstores";
	private static final String dbname = "root";
	private static final String dbpassword ="xxxxxx";

}
