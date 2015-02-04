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
				throw new InsertFailException("����Ŀ¼ʧ��!");
			}
			
		}catch(SQLException e){
			throw new InsertFailException("ϵͳ����!");
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
				throw new UpdateFailException("����Ŀ¼ʧ��!");
			}
			pstmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("ϵͳ����!");
		}
	}
	
	public void delete(int categoryID) throws UpdateFailException{
		String sql = String.format("delete from category where category_id=%d", categoryID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("ɾ��Ŀ¼ʧ��!");
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
				throw new EmptyException("��ѯ���Ϊ��!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("ϵͳ����!");
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
				throw new EmptyException("��ѯ���Ϊ��!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("ϵͳ����!");
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
			throw new EmptyException("ϵͳ����!");
		}
		if(categoryList.size() == 0)
			throw new EmptyException("��ѯ��� Ϊ��!");
		return categoryList;
	}
	
	private PreparedStatement statementAction(Category category, PreparedStatement pstmt) throws SQLException{
		pstmt.setString(1, category.getName());
		return pstmt;
	}
}
