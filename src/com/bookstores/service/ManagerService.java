package com.bookstores.service;
import java.util.List;
import java.util.ArrayList;

import com.bookstores.dao.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;
import com.bookstores.utils.PasswordUtil;

public class ManagerService {
	private int PROCESS = 2;
	private ManagerDao managerDao = null;
	private CategoryDao categoryDao = null;
	private BookDao bookDao = null;
	private BookOrderDao bookOrderDao = null;
	private OrderBooksDao orderBooksDao = null;
	
	public ManagerService(){
		managerDao = new ManagerDao();
		categoryDao = new CategoryDao();
		bookDao = new BookDao();
		bookOrderDao = new BookOrderDao();
		orderBooksDao = new OrderBooksDao();
	}
	
	//����Ա��¼��ע�ᡢ�޸���������
	public Manager login(String email, String password) throws InvalidException, DenyException{
		Manager manager = null;
		try{
			manager = managerDao.queryByEmail(email);
			boolean success = PasswordUtil.validatePassword(email, password, manager.getPassword());
			if(success == false)
				throw new InvalidException("����Ա�������!");
		}catch(EmptyException e){
			manager = null;
			throw new DenyException("���䲻����!");
		}
		return manager;
	}
	
	public Manager create(Manager manager) throws EmailTakenException, InvalidException{
		try{
			Manager newManager = managerDao.queryByEmail(manager.getEmail());
			if(newManager != null)
				throw new EmailTakenException("�����ѱ�ע��!");
		}catch(EmptyException e){
			try{
				String password = PasswordUtil.createPasswordHash(manager.getEmail(), manager.getPassword());
				manager.setPassword(password);
				int managerID = managerDao.insert(manager);
				manager.setManagerID(managerID);
			}catch(InsertFailException insertFail){
				insertFail.printStackTrace();
				throw new InvalidException("ע��ʧ��!");
			}
		}
		return manager;
	}
	
	public void updateInfo(Manager manager) throws DenyException{
		try{
			managerDao.update(manager);
		}catch(UpdateFailException e){
			throw new DenyException("�޸�����ʧ��!");
		}
	}
	
	//Ŀ¼��ɾ������
	public Category addCategory(Category category) throws InvalidException{
		try{
			categoryDao.queryByName(category.getName());
			try{
				categoryDao.update(category);
			}catch(UpdateFailException ue){
				throw new InvalidException(ue.getMessage());
			}
		}catch(EmptyException empty){
			try{
				int categoryID = categoryDao.insert(category);
				category.setCategoryID(categoryID);
			}catch(InsertFailException insert){
				throw new InvalidException("����Ŀ¼ʧ��!");
			}
		}
		return category;
	}
	
	public void updateCategory(Category category) throws InvalidException{
		try{
			categoryDao.update(category);
		}catch(UpdateFailException e){
			throw new InvalidException("����Ŀ¼ʧ��!");
		}
	}
	
	public void removeCategory(Category category) throws InvalidException{
		try{
			categoryDao.delete(category.getCategoryID());
		}catch(UpdateFailException e){
			throw new InvalidException("ɾ��Ŀ¼ʧ��!");
		}
	}
	
	public void removeCategory(int categoryID) throws InvalidException{
		try{
			categoryDao.delete(categoryID);
		}catch(UpdateFailException e){
			throw new InvalidException("ɾ��Ŀ¼ʧ��!");
		}
	}
	
	public Category queryCategroyByID(int categoryID) throws InvalidException{
		Category category = null;
		try{
			category = categoryDao.queryByCategoryID(categoryID);
		}catch(EmptyException e){
			throw new InvalidException(e.getMessage());
		}
		return category;
	}
	
	public List<Category> getCategoryList() throws InvalidException{
		List<Category> categoryList = null;
		try{
			categoryList = categoryDao.queryAll();
		}catch(EmptyException e){
			categoryList = new ArrayList<>();
			throw new InvalidException("Ŀ¼Ϊ��!");
		}
		return categoryList;
	}
	
	//ͼ���������
	public Book addBook(Book book) throws InvalidException{
		try{
			bookDao.queryByISBN(book.getISBN());
			try{
				bookDao.update(book);
			}catch(UpdateFailException ue){
				throw new InvalidException(ue.getMessage());
			}
		}catch(EmptyException e){
			try{
				int bookID = bookDao.insert(book);
				book.setBookID(bookID);
			}catch(InsertFailException insert){
				throw new InvalidException("����ͼ��ʧ��!");
			}
		}
		return book;
	}
	
	public void updateBook(Book book) throws InvalidException{
		try{
			bookDao.update(book);
		}catch(UpdateFailException e){
			e.printStackTrace();
			throw new InvalidException("�޸�ͼ������ʧ��!");
		}
	}
	
	public void removeBook(Book book) throws InvalidException{
		try{
			bookDao.delete(book.getBookID());
		}catch(UpdateFailException e){
			throw new InvalidException("ɾ��ͼ��ʧ��!");
		}
	}
	
	//������������
	public List<BookOrder> getBookOrderList(int status) throws InvalidException{
		List<BookOrder> bookOrderList = null;
		try{
			int ALL=0;
			bookOrderList = bookOrderDao.queryByStatus(ALL, status);
		}catch(EmptyException e){
			bookOrderList = new ArrayList<>();
			throw new InvalidException("�����б�Ϊ��!");
		}
		return bookOrderList;
	}
	
	public List<OrderBooks> viewOrderConent(BookOrder bookOrder) throws InvalidException{
		List<OrderBooks> orderBooksList = null;
		try{
			orderBooksList = orderBooksDao.queryByOrderID(bookOrder.getBookOrderID());
		}catch(EmptyException e){
			orderBooksList = new ArrayList<>();
			throw new InvalidException("��������Ϊ��!");
		}
		return orderBooksList;
	}
	
	public void processOrder(BookOrder bookOrder) throws InvalidException{
		try{
			bookOrder.setStatus(PROCESS);
			bookOrderDao.update(bookOrder);
		}catch(UpdateFailException e){
			throw new InvalidException("����������!");
		}
	}
	
	
	public BookOrder queryOrderByID(int bookOrderID){
		BookOrder bookOrder = null;
		try{
			bookOrder = bookOrderDao.queryByID(bookOrderID);
		}catch(EmptyException e){
			e.printStackTrace();
		}
		return bookOrder;
	}
	
}
