package com.bookstores.service;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import com.bookstores.dao.*;
import com.bookstores.utils.*;
import com.bookstores.domain.*;
import com.bookstores.exception.*;

public class UserService {
	private BookDao bookDao = null;
	private BookUserDao  bookUserDao = null;
	private BookOrderDao bookOrderDao = null;
	private OrderBooksDao orderBooksDao = null;
	private CollectionDao collectionDao = null;
	private BookUserCommentDao commentDao = null;
	private int PAY = 1;
	
	public UserService(){
		bookDao = new BookDao();
		bookUserDao = new BookUserDao();
		bookOrderDao = new BookOrderDao();
		orderBooksDao = new OrderBooksDao();
		collectionDao = new CollectionDao();
		commentDao = new BookUserCommentDao();
	}
	
	public BookUser login(String email, String password) throws InvalidException, UserDoesNotExistException{
		BookUser bookUser = null;
		try{
			bookUser = bookUserDao.queryByEmail(email);
			boolean success = PasswordUtil.validatePassword(email, password, bookUser.getPassword());
			if(success == false){
				bookUser = null;
				throw new InvalidException("密码错误!");
			}else{
				SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
				String currentTime = simpleFormat.format(new Date());
				bookUser.setLasLoginTime(currentTime);
				try{					
					bookUserDao.update(bookUser);
				}catch(UpdateFailException upex){
					upex.printStackTrace();
				}
			}
		}catch(EmptyException e){
			e.printStackTrace();
			throw new UserDoesNotExistException("邮箱不存在!");
		}
		return bookUser;
	}
	
	public BookUser register(BookUser bookUser) throws EmailTakenException, InvalidException{
		BookUser rBookUser = null;
		try{
			rBookUser = bookUserDao.queryByEmail(bookUser.getEmail());
			if( rBookUser != null)
				throw new EmailTakenException("邮箱已经注册!");
		}catch(EmptyException e){
			try{
				String password = PasswordUtil.createPasswordHash(bookUser.getEmail(), bookUser.getPassword());
				bookUser.setPassword(password);
				int bookUserID = bookUserDao.insert(bookUser);
				bookUser.setBookUserID(bookUserID);
			}catch(UpdateFailException upex){
				upex.printStackTrace();
				throw new InvalidException("注册失败!");
			}
		}
		return bookUser;
	}
	
	public void updateInfo(BookUser bookUser) throws InvalidException{
		try{
			bookUserDao.update(bookUser);
		}catch(UpdateFailException e){
			throw new InvalidException("更新用户资料失败!");
		}
	}
	
	public BookOrder createCart(BookOrder bookOrder) throws InvalidException{
		try{
			int orderID = bookOrderDao.insert(bookOrder);
			bookOrder.setBookOrderID(orderID);
		}catch(InsertFailException e){
			e.printStackTrace();
			throw new InvalidException("初始化购物车失败!"+e.getMessage());
		}
		return bookOrder;
	}
	
	public OrderBooks addToCart(BookOrder bookOrder, OrderBooks orderBooks)
			throws InvalidException {
		orderBooks.setOrderID(bookOrder.getBookOrderID());
		try {
			int orderBooksID = orderBooksDao.insert(orderBooks);
			orderBooks.setOrderBooksID(orderBooksID);
			double amount = bookOrder.getAmount();
			try {
				Book book = bookDao.queryByID(orderBooks.getBookID());
				amount += orderBooks.getCount() * book.getPrice();
				bookOrder.setAmount(amount);
				bookOrderDao.update(bookOrder);
			} catch (EmptyException empty) {
				empty.printStackTrace();
			} catch (UpdateFailException update) {
				update.printStackTrace();
			}
		} catch (InsertFailException insertFail) {
			insertFail.printStackTrace();
			throw new InvalidException("插入书籍失败!");
		}
		return orderBooks;
	}
	
	public OrderBooks queryOrderBooksByID(int bookID, int orderID) throws EmptyException{
		OrderBooks orderBooks = null;
		try{
			orderBooks = orderBooksDao.queryByBookIDAndOrderID(bookID, orderID);
		}catch(EmptyException e){
			throw new EmptyException(e.getMessage());
		}
		return orderBooks;
	}
	
	public void updateOrderBooks(OrderBooks orderBooks, int oriCount) throws InvalidException{
		try{
			orderBooksDao.update(orderBooks);
			int bookID = orderBooks.getBookID();
			int bookOrderID = orderBooks.getOrderID();
			try{
				Book book = bookDao.queryByID(bookID);
				BookOrder bookOrder = bookOrderDao.queryByID(bookOrderID);
				double amount = bookOrder.getAmount();
				amount += book.getPrice()*(orderBooks.getCount()-oriCount);
				bookOrder.setAmount(amount);
				bookOrderDao.update(bookOrder);
				if(orderBooks.getCount() == 0){
					orderBooksDao.delete(orderBooks.getOrderBooksID());
				}
			}catch(EmptyException emptyException){
				emptyException.printStackTrace();
			}catch(UpdateFailException updateFail){
				updateFail.printStackTrace();
			}
		}catch(UpdateFailException e){
			e.printStackTrace();
		}
	}
	
	public void removeOrderBooks(OrderBooks orderBooks) throws InvalidException{
		try{
			int bookOrderID = orderBooks.getOrderID();
			int bookID = orderBooks.getBookID();
			BookOrder bookOrder = bookOrderDao.queryByID(bookOrderID);
			Book book = bookDao.queryByID(bookID);
			double amount = bookOrder.getAmount();
			amount -= orderBooks.getCount() * book.getPrice();
			bookOrder.setAmount(amount);
			bookOrderDao.update(bookOrder);
			orderBooksDao.delete(orderBooks.getOrderBooksID());
		}catch(EmptyException e){
			throw new InvalidException("查询结果为空!");
		}catch(UpdateFailException upe){
			throw new InvalidException("更新失败!");
		}
	}
	
	public void removeCart(BookOrder bookOrder) throws InvalidException{
		try{
			int bookOrderID = bookOrder.getBookOrderID();
			List<OrderBooks> orderBooksList = orderBooksDao.queryByOrderID(bookOrderID);
			for(OrderBooks orderBooks: orderBooksList){
				orderBooksDao.delete(orderBooks.getOrderBooksID());
			}
			bookOrderDao.delete(bookOrderID);
		}catch(EmptyException emex){
			throw new InvalidException("查询结果为空!");
		}catch(UpdateFailException upex){
			throw new InvalidException("删除购物车失败!");
		}
	}
	
	public List<OrderBooks> viewCartContent(BookOrder bookOrder) throws InvalidException{
		List<OrderBooks> orderBooksList = null;
		try{
			int bookOrderID = bookOrder.getBookOrderID();
			orderBooksList = orderBooksDao.queryByOrderID(bookOrderID);
		}catch(EmptyException e){
			orderBooksList = new ArrayList<>();
			throw new InvalidException("订单内容为空!");
		}
		return orderBooksList;
	}
	
	public List<BookOrder> viewHistoryOrder(BookUser bookUser) throws InvalidException{
		List<BookOrder> bookOrderList = null;
		try{
			int bookUserID = bookUser.getBookUserID();
			bookOrderList = bookOrderDao.queryByBookUserID(bookUserID);
		}catch(EmptyException e){
			throw new InvalidException("历史订单为空!");
		}
		return bookOrderList;
	}
	
	public List<BookOrder> viewOrderByStatus(BookUser bookUser, int status){
		List<BookOrder> bookOrderList = null;
		try{
			int bookUserID = bookUser.getBookUserID();
			bookOrderList = bookOrderDao.queryByStatus(bookUserID, status);
		}catch(EmptyException e){
			bookOrderList = new ArrayList<>();
		}
		return bookOrderList;
	}
	
	public BookOrder payForCart(BookOrder bookOrder) throws InvalidException{
		try{
			bookOrder.setStatus(PAY);
			bookOrderDao.update(bookOrder);
		}catch(UpdateFailException e){
			throw new InvalidException("付款失败!");
		}
		return bookOrder;
	}
	
	public BookOrder queryCartByID(int cartID) throws InvalidException{
		BookOrder bookOrder = null;
		try{
			bookOrder = bookOrderDao.queryByID(cartID);
		}catch(EmptyException e){
			throw new InvalidException("查询结果为空!");
		}
		return bookOrder;
	}
	
	public UserCollection queryCollectionByID(int collectionID) throws InvalidException{
		UserCollection collection = null;
		try{
			collection = collectionDao.queryByCollectionID(collectionID);
		}catch(EmptyException e){
			throw new InvalidException("查询结果为空!");
		}
		return collection;
	}
	
	public List<UserCollection> queryCollectionByUserID(int userID) throws InvalidException{
		List<UserCollection> collectionList = null;
		try{
			collectionList = collectionDao.queryByUserID(userID);
		}catch(EmptyException e){
			throw new InvalidException("查询结果为空!");
		}
		return collectionList;
	}
	
	public UserCollection addCollection(UserCollection collection) throws InvalidException{
		try{
			UserCollection tCollection = collectionDao.queryByUserCollection(collection.getBookUserID(), collection.getBookID());
			if (tCollection != null)
				throw new InvalidException("已存在收藏!");
		}catch(EmptyException e){
			try{
				int collectionID = collectionDao.insert(collection);
				collection.setCollectionID(collectionID);
			}catch(InsertFailException insertFail){
				insertFail.printStackTrace();
				throw new InvalidException("插入收藏失败!");
			}
		}
		return collection;
	}
	
	public void removeCollection(UserCollection collection) throws InvalidException{
		try{
			collectionDao.delete(collection.getCollectionID());
		}catch(UpdateFailException e){
			throw new InvalidException("删除收藏失败!");
		}
	}
	
	public BookUserComment addComment(BookUserComment comment) throws InvalidException{
		try{
			int commentID = commentDao.insert(comment);
			comment.setBookUserCommentID(commentID);
		}catch(InsertFailException e){
			throw new InvalidException("插入评论失败!");
		}
		return comment;
	}
	
	public void updateComment(BookUserComment comment)throws InvalidException{
		try{
			commentDao.update(comment);
		}catch(UpdateFailException e){
			throw new InvalidException("修改评论失败!");
		}
	}
	
	public void removeComent(BookUserComment comment) throws InvalidException{
		try{
			commentDao.delete(comment.getBookUserCommentID());
		}catch(UpdateFailException e){
			throw new InvalidException("删除评论失败!");
		}
	}
	
	public BookUserComment queryUserCommentByID(int commentID) throws EmptyException{
		BookUserComment comment = null;
		try{
			comment = commentDao.queryByCommentID(commentID);
		}catch(EmptyException e){
			throw e;
		}
		return comment;
	}
	
}
