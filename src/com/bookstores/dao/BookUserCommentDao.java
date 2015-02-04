package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;

public class BookUserCommentDao {
	public BookUserCommentDao(){}
	
	public int insert(BookUserComment bookUserComment) throws InsertFailException{
		String sql ="insert into book_user_comment(book_user_id, book_id, content, comment_time, update_time)"
				+ " values(?, ?, ?, ?, ?)";
		int last_insert_key = -1;
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt = statementAction(bookUserComment, pstmt);
			pstmt.executeUpdate();
			
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
				last_insert_key = keys.getInt(1);
			}else{
				throw new InsertFailException("��������ʧ��!");
			}
			keys.close();
			pstmt.close();
			con.close();
		}catch(SQLException e){
			throw new InsertFailException("ϵͳ����!");
		}
		return last_insert_key;
	}
	
	public void update(BookUserComment bookUserComment) throws UpdateFailException{
		String sql = String.format("update book_user_comment set book_user_id=?, book_id=?, content=?, comment_time=?, update_time=?"
				+ "where book_user_comment_id=%d", bookUserComment.getBookUserCommentID());
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = statementAction(bookUserComment, pstmt);
			int effect_row = pstmt.executeUpdate();
			if(effect_row <= 0){
				throw new UpdateFailException("��������ʧ��!");
			}
			pstmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("ϵͳ����!");
		}
	}
	
	public void delete(int bookUserCommentID) throws UpdateFailException{
		String sql = String.format("delete from book_user_comment where book_user_comment_id=%d", bookUserCommentID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("ɾ������ʧ��!");
	}
	
	public BookUserComment queryByCommentID(int commentID) throws EmptyException{
		BookUserComment bookUserComment = null;
		String sql = String.format("select * from book_user_comment where book_user_comment_id=%d", commentID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				bookUserComment = new BookUserComment();
				bookUserComment.setBookUserCommentID(rs.getInt("book_user_comment_id"));
				bookUserComment.setBookUserID(rs.getInt("book_user_id"));
				bookUserComment.setBookID(rs.getInt("book_id"));
				bookUserComment.setContent(rs.getString("content"));
				bookUserComment.setCommentTime(rs.getString("comment_time"));
				bookUserComment.setUpdateTime(rs.getString("update_time"));
			}else{
				throw new EmptyException("��ѯ���Ϊ��!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("ϵͳ����!");
		}
		return bookUserComment;
	}
	
	public List<BookUserComment> queryByBookID(int bookID) throws EmptyException{
		List<BookUserComment> bookCommentList = new ArrayList<>();
		String sql = String.format("select * from book_user_comment where book_id=%d", bookID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				BookUserComment bookUserComment = new BookUserComment();
				bookUserComment.setBookUserCommentID(rs.getInt("book_user_comment_id"));
				bookUserComment.setBookUserID(rs.getInt("book_user_id"));
				bookUserComment.setBookID(rs.getInt("book_id"));
				bookUserComment.setContent(rs.getString("content"));
				bookUserComment.setCommentTime(rs.getString("comment_time"));
				bookUserComment.setUpdateTime(rs.getString("update_time"));
				bookCommentList.add(bookUserComment);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("ϵͳ����!");
		}
		if(bookCommentList.size() == 0)
			throw new EmptyException("��ѯ��� Ϊ��!");
		return bookCommentList;
	}
	
	private PreparedStatement statementAction(BookUserComment bookUserComment, PreparedStatement pstmt) throws SQLException{
		pstmt.setInt(1, bookUserComment.getBookUserID());
		pstmt.setInt(2, bookUserComment.getBookID());
		pstmt.setString(3, bookUserComment.getContent());
		pstmt.setString(4, bookUserComment.getCommentTime());
		pstmt.setString(5, bookUserComment.getUpdateTime());
		return pstmt;
	}
}
