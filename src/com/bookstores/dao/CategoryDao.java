package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;

public class CategoryDao {
	public CategoryDao(){}
	
	public int insert(Category category) throws InsertFailException{
		String sql ="insert into category(name) values(?);";
		int last_insert_key = -1;
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt = statementAction(category, pstmt);
			pstmt.executeUpdate();
			
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
				last_insert_key = keys.getInt(1);
			}else{
				throw new InsertFailException("插入目录失败!");
			}
			
		}catch(SQLException e){
			throw new InsertFailException("系统错误!");
		}
		return last_insert_key;
	}
	
	public void update(Category category) throws UpdateFailException{
		String sql = String.format("update category set name=? where category_id=%d", category.getCategoryID());
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = statementAction(category, pstmt);
			int effect_row = pstmt.executeUpdate();
			if(effect_row <= 0){
				throw new UpdateFailException("更新目录失败!");
			}
			pstmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("系统错误!");
		}
	}
	
	public void delete(int categoryID) throws UpdateFailException{
		String sql = String.format("delete from category where category_id=%d", categoryID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("删除目录失败!");
	}
	
	public Category queryByCategoryID(int categoryID) throws EmptyException{
		Category category = null;
		String sql = String.format("select * from category where category_id=%d", categoryID);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				category = new Category();
				category.setCategoryID(rs.getInt("category_id"));
				category.setName(rs.getString("name"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return category;
	}
	
	public Category queryByName(String name) throws EmptyException{
		Category category = null;
		String sql = String.format("select * from category where name=\'%s\'", name);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				category = new Category();
				category.setCategoryID(rs.getInt("category_id"));
				category.setName(rs.getString("name"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return category;
	}
	
	public List<Category> queryAll() throws EmptyException{
		List<Category> categoryList = new ArrayList<>();
		String sql = "select * from category";
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				Category category = new Category();
				category.setCategoryID(rs.getInt("category_id"));
				category.setName(rs.getString("name"));
				categoryList.add(category);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(categoryList.size() == 0)
			throw new EmptyException("查询结果 为空!");
		return categoryList;
	}
	
	private PreparedStatement statementAction(Category category, PreparedStatement pstmt) throws SQLException{
		pstmt.setString(1, category.getName());
		return pstmt;
	}
}
