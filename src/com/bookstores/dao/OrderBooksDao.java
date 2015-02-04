package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;

public class OrderBooksDao {
	public OrderBooksDao(){}
	
	public int insert(OrderBooks orderBooks) throws InsertFailException{
		String sql ="insert into order_books(book_id, order_id, count, book_name) values(?, ?, ?, ?);";
		int last_insert_key = -1;
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt = statementAction(orderBooks, pstmt);
			pstmt.executeUpdate();
			
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
				last_insert_key = keys.getInt(1);
			}else{
				throw new InsertFailException("插入添加书籍失败!");
			}
			keys.close();
			pstmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new InsertFailException("系统错误!");
		}
		return last_insert_key;
	}
	
	public void update(OrderBooks orderBooks) throws UpdateFailException{
		String sql = String.format("update order_books set book_id=?, order_id=?, count=?, book_name=?"
				+ " where order_books_id=%d", orderBooks.getOrderBooksID());
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = statementAction(orderBooks, pstmt);
			int effect_row = pstmt.executeUpdate();
			if(effect_row <= 0){
				throw new UpdateFailException("更新失败!");
			}
			pstmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("系统错误!");
		}
	}
	
	public void delete(int orderBooksID) throws UpdateFailException{
		String sql = String.format("delete from order_books where order_books_id=%d", orderBooksID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("删除失败!");
	}
	
	public OrderBooks queryByID(int orderBooksID) throws EmptyException{
		OrderBooks orderBooks = null;
		String sql = String.format("select * from order_books where order_books_id=%d", orderBooksID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				orderBooks = new OrderBooks();
				orderBooks.setOrderBooksID(rs.getInt("order_books_id"));
				orderBooks.setBookID(rs.getInt("book_id"));
				orderBooks.setOrderID(rs.getInt("order_id"));
				orderBooks.setCount(rs.getInt("count"));
				orderBooks.setBookName(rs.getString("book_name"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return orderBooks;
	}
	
	public List<OrderBooks> queryByOrderID(int orderID) throws EmptyException{
		List<OrderBooks> orderBooksList = new ArrayList<>();
		String sql = String.format("select * from order_books where order_id=%d", orderID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				OrderBooks orderBooks = new OrderBooks();
				orderBooks.setOrderBooksID(rs.getInt("order_books_id"));
				orderBooks.setBookID(rs.getInt("book_id"));
				orderBooks.setOrderID(rs.getInt("order_id"));
				orderBooks.setCount(rs.getInt("count"));
				orderBooks.setBookName(rs.getString("book_name"));
				orderBooksList.add(orderBooks);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(orderBooksList.size() == 0)
			throw new EmptyException("查询结果为空!");
		return orderBooksList;
	}
	
	public OrderBooks queryByBookIDAndOrderID(int bookID, int orderID) throws EmptyException{
		OrderBooks orderBooks= null;
		String sql = String.format("select * from order_books where book_id =%d and order_id=%d", bookID, orderID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				orderBooks = new OrderBooks();
				orderBooks.setOrderBooksID(rs.getInt("order_books_id"));
				orderBooks.setBookID(rs.getInt("book_id"));
				orderBooks.setOrderID(rs.getInt("order_id"));
				orderBooks.setCount(rs.getInt("count"));
				orderBooks.setBookName(rs.getString("book_name"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(orderBooks == null)
			throw new EmptyException("查询结果为空!");
		return orderBooks;
	}
	
	private PreparedStatement statementAction(OrderBooks orderBooks, PreparedStatement pstmt) throws SQLException{
		pstmt.setInt(1, orderBooks.getBookID());
		pstmt.setInt(2, orderBooks.getOrderID());
		pstmt.setInt(3, orderBooks.getCount());
		pstmt.setString(4, orderBooks.getBookName());
		return pstmt;
	}
}
