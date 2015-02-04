package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.exception.*;
import com.bookstores.domain.*;

public class BookDao {
	public BookDao(){}
	
	public int insert(Book book) throws InsertFailException{
		String sql = "insert into book(title, ISBN, publisher, published_date, description, cover, price, category_id)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getISBN());
			pstmt.setString(3, book.getPublisher());
			pstmt.setString(4, book.getPublishedDate());
			pstmt.setString(5, book.getDescription());
			pstmt.setString(6, book.getCover());
			pstmt.setDouble(7, book.getPrice());
			pstmt.setInt(8, book.getCategroyID());
			
			pstmt.executeUpdate();
			
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
				int last_insert_key = keys.getInt(1);
				keys.close();
				pstmt.close();
				con.close();
				return last_insert_key;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new InsertFailException(e.getMessage());
		}
		return -1;
	}
	
	public void update(Book book) throws UpdateFailException{
		String sql = String.format("update book set title=?, ISBN=?, publisher=?, published_date=?, description=?, cover=?, price=?, category_id=?"
				+ " where book_id=%d", book.getBookID());
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getISBN());
			pstmt.setString(3, book.getPublisher());
			pstmt.setString(4, book.getPublishedDate());
			pstmt.setString(5, book.getDescription());
			pstmt.setString(6, book.getCover());
			pstmt.setDouble(7, book.getPrice());
			pstmt.setInt(8, book.getCategroyID());
	
			int effect_row = pstmt.executeUpdate();
			if( effect_row <= 0){
				throw new UpdateFailException("修改书籍信息失败!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException(e.getMessage());
		}
	}
	
	public void delete(int bookID) throws UpdateFailException{
		String sql = String.format("delete from book where book_id=%d", bookID);
		String[] args = {};
		boolean result = DBManager.delete(sql, args);
		if(result == false)
			throw new UpdateFailException("删除书籍失败!");
	}
	
	public List<Book> queryAll() throws EmptyException{
		List<Book> bookList = new ArrayList<>();
		String sql = "select * from book";
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				Book book = new Book();
				book.setBookID(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setISBN(rs.getString("ISBN"));
				book.setPublisher(rs.getString("publisher"));
				book.setPublishedDate(rs.getString("published_date"));
				book.setDescription(rs.getString("description"));
				book.setCover(rs.getString("cover"));
				book.setPrice(rs.getDouble("price"));
				book.setCategroyID(rs.getInt("category_id"));
				bookList.add(book);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(bookList.size() == 0)
			throw new EmptyException("书籍为空!");
		return bookList;
	}
	
	public Book queryByID(int bookID) throws EmptyException{
		String sql = String.format("select * from book where book_id=%d", bookID);
		String[] args = {};
		Book book = null;
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				book = new Book();
				book.setBookID(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setISBN(rs.getString("ISBN"));
				book.setPublisher(rs.getString("publisher"));
				book.setPublishedDate(rs.getString("published_date"));
				book.setDescription(rs.getString("description"));
				book.setCover(rs.getString("cover"));
				book.setPrice(rs.getDouble("price"));
				book.setCategroyID(rs.getInt("category_id"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			throw new  EmptyException("系统错误!");
		}
		return book;
	}
	
	public Book queryByISBN(String ISBN) throws EmptyException{
		String sql = String.format("select * from book where ISBN=\'%s\'", ISBN);
		String[] args = {};
		Book book = null;
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				book = new Book();
				book.setBookID(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setISBN(rs.getString("ISBN"));
				book.setPublisher(rs.getString("publisher"));
				book.setPublishedDate(rs.getString("published_date"));
				book.setDescription(rs.getString("description"));
				book.setCover(rs.getString("cover"));
				book.setPrice(rs.getDouble("price"));
				book.setCategroyID(rs.getInt("category_id"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			throw new  EmptyException("系统错误!");
		}
		return book;
	}
	
	public List<Book> queryByTitle(String title) throws EmptyException{
		List<Book> bookList = new ArrayList<>();
		String sql = String.format("select * from book where title=\'%s\'", title);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				Book book = new Book();
				book.setBookID(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setISBN(rs.getString("ISBN"));
				book.setPublisher(rs.getString("publisher"));
				book.setPublishedDate(rs.getString("published_date"));
				book.setDescription(rs.getString("description"));
				book.setCover(rs.getString("cover"));
				book.setPrice(rs.getDouble("price"));
				book.setCategroyID(rs.getInt("category_id"));
				bookList.add(book);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(bookList.size() == 0)
			throw new EmptyException("书籍为空!");
		return bookList;
	}
	
	public List<Book> queryByCategory(int categoryID) throws EmptyException{
		List<Book> bookList = new ArrayList<>();
		String sql = String.format("select * from book where category_id=%d", categoryID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				Book book = new Book();
				book.setBookID(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setISBN(rs.getString("ISBN"));
				book.setPublisher(rs.getString("publisher"));
				book.setPublishedDate(rs.getString("published_date"));
				book.setDescription(rs.getString("description"));
				book.setCover(rs.getString("cover"));
				book.setPrice(rs.getDouble("price"));
				book.setCategroyID(rs.getInt("category_id"));
				bookList.add(book);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(bookList.size() == 0)
			throw new EmptyException("书籍为空!");
		return bookList;
	}
}
