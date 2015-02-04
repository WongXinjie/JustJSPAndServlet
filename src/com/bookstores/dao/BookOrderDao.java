package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.domain.BookOrder;
import com.bookstores.exception.*;

public class BookOrderDao {
	public BookOrderDao(){}
	
	public int insert(BookOrder bookOrder) throws InsertFailException{
		String sql = "insert into book_order(book_user_id, address_id, amount, status, order_time, pay_date) "
				+ " values(?, ?, ?, ?, ?, ?)";
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt = statementAction(bookOrder, pstmt);
			pstmt.executeUpdate();
			
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
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
			throw new InsertFailException("插入订单失败!"+e.getMessage());
		}
	}
	
	public void update(BookOrder bookOrder) throws UpdateFailException{
		String sql = String.format("update book_order set book_user_id=?, address_id=?, amount=?, status=?, order_time=?, pay_date=? where "
				+ " book_order_id=%d", bookOrder.getBookOrderID());
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = statementAction(bookOrder, pstmt);
			int effect_row = pstmt.executeUpdate();
			if(effect_row <= 0)
				throw new UpdateFailException("修改订单失败!");
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("系统错误!");
		}
	}
	
	public void delete(int bookOrderID) throws UpdateFailException{
		String sql = String.format("delete from book_order where book_order_id=%d", bookOrderID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("删除订单失败!");
	}
	
	public List<BookOrder> queryAll() throws EmptyException{
		List<BookOrder> bookOrderList = new ArrayList<>();
		String sql = "select * from book_order";
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				BookOrder bookOrder = new BookOrder();
				bookOrder.setBookOrderID(rs.getInt("book_order_id"));
				bookOrder.setBookUserID(rs.getInt("book_user_id"));
				bookOrder.setAddressID(rs.getInt("address_id"));
				bookOrder.setAmount(rs.getDouble("amount"));
				bookOrder.setStatus(rs.getInt("status"));
				bookOrder.setOrderTime(rs.getString("order_time"));
				bookOrder.setPayTime(rs.getString("pay_date"));
				bookOrderList.add(bookOrder);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(bookOrderList.size() == 0)
			throw new EmptyException("查询结果为空!");
		return bookOrderList;
	}
	
	public BookOrder queryByID(int bookOrderID) throws EmptyException{
		BookOrder bookOrder = null;
		String sql = String.format("select * from book_order where book_order_id=%d", bookOrderID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				bookOrder = new BookOrder();
				bookOrder.setBookOrderID(rs.getInt("book_order_id"));
				bookOrder.setBookUserID(rs.getInt("book_user_id"));
				bookOrder.setAddressID(rs.getInt("address_id"));
				bookOrder.setAmount(rs.getDouble("amount"));
				bookOrder.setStatus(rs.getInt("status"));
				bookOrder.setOrderTime(rs.getString("order_time"));
				bookOrder.setPayTime(rs.getString("pay_date"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(bookOrder == null)
			throw new EmptyException("查询结果为空!");
		return bookOrder;
	}
	
	public List<BookOrder> queryByBookUserID(int bookUserID) throws EmptyException{
		List<BookOrder> bookOrderList = new ArrayList<>();
		String sql = String.format("select * from book_order where book_user_id=%d", bookUserID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				BookOrder bookOrder = new BookOrder();
				bookOrder.setBookOrderID(rs.getInt("book_order_id"));
				bookOrder.setBookUserID(rs.getInt("book_user_id"));
				bookOrder.setAddressID(rs.getInt("address_id"));
				bookOrder.setAmount(rs.getDouble("amount"));
				bookOrder.setStatus(rs.getInt("status"));
				bookOrder.setOrderTime(rs.getString("order_time"));
				bookOrder.setPayTime(rs.getString("pay_date"));
				bookOrderList.add(bookOrder);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(bookOrderList.size() == 0)
			throw new EmptyException("查询结果为空!");
		return bookOrderList;
	}
	
	//bookUserID=0 查询全部
	public List<BookOrder> queryByStatus(int bookUserID, int status) throws EmptyException{
		List<BookOrder> bookOrderList = new ArrayList<>();
		String sql = null;
		if(bookUserID == 0){
			sql = String.format("select * from book_order where status=%d", status);
		}else{
			sql = String.format("select * from book_order where book_user_id=%d and status=%d", bookUserID, status);
		}
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				BookOrder bookOrder = new BookOrder();
				bookOrder.setBookOrderID(rs.getInt("book_order_id"));
				bookOrder.setBookUserID(rs.getInt("book_user_id"));
				bookOrder.setAddressID(rs.getInt("address_id"));
				bookOrder.setAmount(rs.getDouble("amount"));
				bookOrder.setStatus(rs.getInt("status"));
				bookOrder.setOrderTime(rs.getString("order_time"));
				bookOrder.setPayTime(rs.getString("pay_date"));
				bookOrderList.add(bookOrder);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(bookOrderList.size() == 0)
			throw new EmptyException("查询结果为空!");
		return bookOrderList;
		
	}
	
	private PreparedStatement statementAction(BookOrder bookOrder, PreparedStatement pstmt) throws SQLException{
		pstmt.setInt(1, bookOrder.getBookUserID());
		pstmt.setInt(2, bookOrder.getAddressID());
		pstmt.setDouble(3, bookOrder.getAmount());
		pstmt.setInt(4, bookOrder.getStatus());
		pstmt.setString(5, bookOrder.getOrderTime());
		pstmt.setString(6, bookOrder.getPayTime());
		return pstmt;
	}
	
}
