package com.bookstores.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bookstores.domain.*;
import com.bookstores.cons.*;
import com.bookstores.exception.*;

public class AddressDao {
	public AddressDao() {
	}
	
	public void insert(Address address) throws InsertFailException {
		String sql = "insert into address(country, province, city, district, street, zip) values(?, ?, ?, ?, ?, ?);";
		String[] args = { address.getCountry(), address.getProvince(),
				address.getCity(), address.getDistrict(), address.getStreet(),
				address.getZip() };
		int result = DBManager.insert(sql, args);
		if (result == -1) {
			throw new InsertFailException("插入地址记录失败!");
		}
	}

	public void update(Address address) throws UpdateFailException {
		String sql = String
				.format("update address set contry=?, province=?, city=?, district=?, street=?, zip=? where address_id=%d",
						address.getAddressID());
		String[] args = { address.getCountry(), address.getProvince(),
				address.getCity(), address.getDistrict(), address.getStreet(),
				address.getZip() };
		boolean result = DBManager.update(sql, args);
		if (result == false) {
			throw new UpdateFailException("更新地址失败");
		}
	}

	public Address queryByID(int addressID) throws EmptyException {
		String sql = String.format("select * from address where address_id=%d",
				addressID);
		String[] args = {};
		ResultSet rs = DBManager.query(sql, args);
		if (rs == null)
			throw new EmptyException("地址不存在!");
		try {
			if (rs.next()) {
				Address address = new Address();
				address.setAddressID(rs.getInt("address_id"));
				address.setCountry(rs.getString("country"));
				address.setProvince(rs.getString("province"));
				address.setCity(rs.getString("city"));
				address.setDistrict(rs.getString("district"));
				address.setStreet(rs.getString("street"));
				address.setZip(rs.getString("zip"));
				return address;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(int addressID) throws UpdateFailException{
		String sql = String.format("delete from address where address_id=%d;", addressID);
		String[] args = {};
		boolean result = DBManager.delete(sql, args);
		if(result == false)
			throw new UpdateFailException("删除地址失败!");
	}

}
