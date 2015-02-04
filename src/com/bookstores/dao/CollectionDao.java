package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;

public class CollectionDao{
	public CollectionDao(){}
	
	public int insert(UserCollection collection) throws InsertFailException{
		String sql = "insert into collection(book_user_id, book_id, add_time) values(?, ?, ?);";
		int last_insert_key = -1;
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt = statementAction(collection, pstmt);
			pstmt.executeUpdate();
			
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
				last_insert_key = keys.getInt(1);
			}else{
				throw new InsertFailException("插入收藏失败!");
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
	
	public void delete(int collectionID) throws UpdateFailException{
		String sql = String.format("delete from collection where collection_id=%d", collectionID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("删除收藏失败!");
	}
	
	public UserCollection queryByCollectionID(int collectionID) throws EmptyException{
		UserCollection collection = null;
		String sql = String.format("select * from collection where collection_id=%d", collectionID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				collection = new UserCollection();
				collection.setCollectionID(rs.getInt("collection_id"));
				collection.setBookUserID(rs.getInt("book_user_id"));
				collection.setBookID(rs.getInt(rs.getInt("book_id")));
				collection.setAddTime(rs.getString("add_time"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return collection;
	}
	
	public UserCollection queryByUserCollection(int bookUserID, int bookID) throws EmptyException{
		UserCollection collection = null;
		String sql = String.format("select * from collection where book_user_id=%d and book_id=%d", bookUserID, bookID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				collection = new UserCollection();
				collection.setCollectionID(rs.getInt("collection_id"));
				collection.setBookUserID(rs.getInt("book_user_id"));
				collection.setBookID(rs.getInt(rs.getInt("book_id")));
				collection.setAddTime(rs.getString("add_time"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return collection;
	}
	
	public List<UserCollection> queryByUserID(int userID) throws EmptyException{
		List<UserCollection> collectionList = new ArrayList<>();
		String sql = String.format("select * from collection where book_user_id=%d", userID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				UserCollection collection = new UserCollection();
				collection.setCollectionID(rs.getInt("collection_id"));
				collection.setBookUserID(rs.getInt("book_user_id"));
				collection.setBookID(rs.getInt(rs.getInt("book_id")));
				collection.setAddTime(rs.getString("add_time"));
				collectionList.add(collection);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(collectionList.size() == 0)
			throw new EmptyException("查询结果 为空!");
		return collectionList;
	}
	
	private PreparedStatement statementAction(UserCollection collection, PreparedStatement pstmt) throws SQLException{
		pstmt.setInt(1, collection.getBookUserID());
		pstmt.setInt(2, collection.getBookID());
		pstmt.setString(3, collection.getAddTime());
		return pstmt;
	}
}
