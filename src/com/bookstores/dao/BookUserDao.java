package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;

public class BookUserDao {
	public BookUserDao(){}
	
	public List<BookUser> queryAll() throws EmptyException{
		List<BookUser> bookUserList = new ArrayList<>();
		String sql = "select * from book_user;";
		String[] args = {};
		
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				BookUser bookUser = new BookUser();
				bookUser.setBookUserID(rs.getInt("book_user_id"));
				bookUser.setBookUserName(rs.getString("book_user_name"));
				bookUser.setEmail(rs.getString("email"));
				bookUser.setPassword(rs.getString("password"));
				bookUser.setAddressID(rs.getInt("address_id"));
				bookUser.setRegisteredTime(rs.getString("registered_time"));
				bookUser.setLasLoginTime(rs.getString("last_login_time"));
				bookUserList.add(bookUser);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(bookUserList.size() == 0)
			throw new EmptyException("查询结果为空!");
		return bookUserList;
	}
	
	public int insert(BookUser bookUser) throws UpdateFailException{
		String sql = "insert into book_user(book_user_name, email, password, address_id, registered_time, last_login_time) "
				+ "values(?, ?, ?, ?, ?, ?)";
		int last_insert_key = -1;
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt = statementAction(bookUser, pstmt);
			pstmt.executeUpdate();
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
				last_insert_key = keys.getInt(1);
			}else{
				throw new UpdateFailException("插入新用户失败!");
			}
			keys.close();
			pstmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("系统错误!");
		}
		return last_insert_key;
	}
	
	public void update(BookUser bookUser) throws UpdateFailException{
		String sql = String.format("update book_user set book_user_name=?, email=?, password=?, address_id=?, registered_time=?, last_login_time=?"
				+ " where book_user_id=%d", bookUser.getBookUserID());
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = statementAction(bookUser, pstmt);
			int effect_row = pstmt.executeUpdate();
			if(effect_row <= 0)
				throw new UpdateFailException("更新用户资料错误!");
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("系统错误!");
		}
	}
	
	public void delete(int bookUserID) throws UpdateFailException{
		String sql = String.format("delete from book_user where book_user_id=%d", bookUserID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("删除用户失败!");
	}
	
	public BookUser queryByEmail(String email) throws EmptyException{
		BookUser bookUser = null;
		String sql = String.format("select * from book_user where email=\'%s\'", email);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				bookUser = new BookUser();
				bookUser.setBookUserID(rs.getInt("book_user_id"));
				bookUser.setBookUserName(rs.getString("book_user_name"));
				bookUser.setEmail(rs.getString("email"));
				bookUser.setPassword(rs.getString("password"));
				bookUser.setAddressID(rs.getInt("address_id"));
				bookUser.setRegisteredTime(rs.getString("registered_time"));
				bookUser.setLasLoginTime(rs.getString("last_login_time"));
			}else{
				throw new EmptyException("用户不存在!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return bookUser;
	}
	
	public BookUser queryByName(String name) throws EmptyException{
		BookUser bookUser = null;
		String sql = String.format("select * from book_user where book_user_name=\'%s\';", name);
		String[] args = {};
		
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				bookUser = new BookUser();
				bookUser.setBookUserID(rs.getInt("book_user_id"));
				bookUser.setBookUserName(rs.getString("book_user_name"));
				bookUser.setEmail(rs.getString("email"));
				bookUser.setPassword(rs.getString("password"));
				bookUser.setAddressID(rs.getInt("address_id"));
				bookUser.setRegisteredTime(rs.getString("registered_time"));
				bookUser.setLasLoginTime(rs.getString("last_login_time"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(bookUser == null)
			throw new EmptyException("查询结果为空!");
		return bookUser;
	}
	
	public BookUser queryByEmailAndPassword(String email, String password) throws EmptyException{
		BookUser bookUser = null;
		String sql = String.format("select * from book_user where email=%s and password=\'%s\';", email, password);
		String[] args = {};
		
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				bookUser = new BookUser();
				bookUser.setBookUserID(rs.getInt("book_user_id"));
				bookUser.setBookUserName(rs.getString("book_user_name"));
				bookUser.setEmail(rs.getString("email"));
				bookUser.setPassword(rs.getString("password"));
				bookUser.setAddressID(rs.getInt("address_id"));
				bookUser.setRegisteredTime(rs.getString("registered_time"));
				bookUser.setLasLoginTime(rs.getString("last_login_time"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(bookUser == null)
			throw new EmptyException("查询结果为空!");
		return bookUser;
	}
	
	private PreparedStatement statementAction(BookUser bookUser, PreparedStatement pstmt) throws SQLException{
		pstmt.setString(1, bookUser.getBookUserName());
		pstmt.setString(2,  bookUser.getEmail());
		pstmt.setString(3,  bookUser.getPassword());
		pstmt.setInt(4, bookUser.getAddressID());
		pstmt.setString(5, bookUser.getRegisteredTime());
		pstmt.setString(6, bookUser.getLastLoginTime());
		return pstmt;
	}
}
