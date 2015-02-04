package com.bookstores.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.cons.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;

public class ManagerDao {
	public ManagerDao(){}
	
	public List<Manager> queryAll() throws EmptyException{
		List<Manager> managerList = new ArrayList<>();
		String sql = "select * from manager;";
		String[] args = {};
		
		try{
			ResultSet rs = DBManager.query(sql, args);
			while(rs.next()){
				Manager manager = new Manager();
				manager.setManagerID(rs.getInt("manager_id"));
				manager.setManagerName(rs.getString("manager_name"));
				manager.setEmail(rs.getString("email"));
				manager.setPermission(rs.getInt("permission"));
				manager.setCreateTime(rs.getString("create_time"));
				manager.setLastLoginTime(rs.getString("last_login_time"));
				managerList.add(manager);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		if(managerList.size() == 0)
			throw new EmptyException("查询结果为空!");
		return managerList;
	}
	
	public int insert(Manager manager) throws InsertFailException{
		String sql = "insert into manager(manager_name, email, permission, create_time, last_login_time, password) "
				+ "values(?, ?, ?, ?, ?, ?)";
		int last_insert_key = -1;
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt = statementAction(manager, pstmt);
			pstmt.executeUpdate();
			ResultSet keys = pstmt.getGeneratedKeys();
			if(keys.next()){
				last_insert_key = keys.getInt(1);
			}else{
				throw new InsertFailException("插入管理员失败!");
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
	
	public void update(Manager manager) throws UpdateFailException{
		String sql = String.format("update manager set manager_name=?, email=?, permission=?, create_time=?, last_login_time=?, password=?"
				+ " where book_user_id=%d", manager.getManagerID());
		try{
			Connection con = DBManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt = statementAction(manager, pstmt);
			int effect_row = pstmt.executeUpdate();
			if(effect_row <= 0)
				throw new UpdateFailException("更新管理员资料错误!");
		}catch(SQLException e){
			e.printStackTrace();
			throw new UpdateFailException("系统错误!");
		}
	}
	
	public void delete(int managerID) throws UpdateFailException{
		String sql = String.format("delete from manager where manager_id=%d", managerID);
		String[] args = {};
		boolean success = DBManager.delete(sql, args);
		if(success == false)
			throw new UpdateFailException("删除管理员失败!");
	}
	
	public Manager queryByEmail(String email) throws EmptyException{
		Manager manager = null;
		String sql = String.format("select * from manager where email=\'%s\'", email);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				manager = new Manager();
				manager.setManagerID(rs.getInt("manager_id"));
				manager.setManagerName(rs.getString("manager_name"));
				manager.setEmail(rs.getString("email"));
				manager.setPermission(rs.getInt("permission"));
				manager.setCreateTime(rs.getString("create_time"));
				manager.setLastLoginTime(rs.getString("last_login_time"));
				manager.setPassword(rs.getString("password"));
			}else{
				throw new EmptyException("插叙结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return manager;
	}
	
	public Manager queryByName(String name) throws EmptyException{
		Manager manager = null;
		String sql = String.format("select * from manager where manager_name=\'%s\';", name);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				manager = new Manager();
				manager.setManagerID(rs.getInt("manager_id"));
				manager.setManagerName(rs.getString("manager_name"));
				manager.setEmail(rs.getString("email"));
				manager.setPermission(rs.getInt("permission"));
				manager.setCreateTime(rs.getString("create_time"));
				manager.setLastLoginTime(rs.getString("last_login_time"));
				manager.setPassword(rs.getString("password"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return manager;
	}
	
	public Manager queryByEmailAndPassword(String email, String password) throws EmptyException{
		Manager manager = null;
		String sql = String.format("select * from manager where email=\'%s\' and password=\'%s\';", email, password);
		String[] args = {};
		try{
			ResultSet rs = DBManager.query(sql, args);
			if(rs.next()){
				manager = new Manager();
				manager.setManagerID(rs.getInt("manager_id"));
				manager.setManagerName(rs.getString("manager_name"));
				manager.setEmail(rs.getString("email"));
				manager.setPermission(rs.getInt("permission"));
				manager.setCreateTime(rs.getString("create_time"));
				manager.setLastLoginTime(rs.getString("last_login_time"));
				manager.setPassword(rs.getString("password"));
			}else{
				throw new EmptyException("查询结果为空!");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new EmptyException("系统错误!");
		}
		return manager;
	}
	
	private PreparedStatement statementAction(Manager manager, PreparedStatement pstmt) throws SQLException{
		pstmt.setString(1, manager.getManagerName());
		pstmt.setString(2, manager.getEmail());
		pstmt.setInt(3, manager.getPermission());
		pstmt.setString(4, manager.getCreateTime());
		pstmt.setString(5, manager.getLastLoginTime());
		pstmt.setString(6, manager.getPassword());
		return pstmt;
	}
}
