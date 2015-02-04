package com.bookstores.service;
import java.util.List;
import java.util.ArrayList;
import com.bookstores.domain.*;
import com.bookstores.exception.*;
import com.bookstores.dao.*;

public class BookService {
	private int booksEachPage = 10;
	private BookDao bookDao = null;
	private CategoryDao categoryDao = null;
	private BookUserCommentDao bookUserCommentDao=null;
	
	public BookService(){
		bookDao = new BookDao();
		bookUserCommentDao = new BookUserCommentDao();
		categoryDao = new CategoryDao();
	}
	
	public List<Book> getBookList(int page){
		List<Book> bookList = null;
		try{
			bookList = bookDao.queryAll();
			return paginate(bookList,page);
		}catch(EmptyException e){
			bookList = new ArrayList<>();
			return bookList;
		}
	}
	
	public int getPaginator(){
		int totalPage = 0;
		try{
			List<Book> bookList = bookDao.queryAll();
			int size = bookList.size();
			totalPage = size/booksEachPage +1;
		}catch(EmptyException e){
			totalPage = 0;
		}
		return totalPage;
	}
	
	public Book getBookByID(int bookID){
		Book book = null;
		try{
			book = bookDao.queryByID(bookID);
		}catch(EmptyException e){
			e.printStackTrace();
		}
		return book;
	}
	
	
	
	public Book getBookByISBN(String ISBN){
		Book book = null;
		try{
			book = bookDao.queryByISBN(ISBN);
		}catch(EmptyException e){
			e.printStackTrace();
		}
		return book;
	}
	
	public List<Book> getBookListByTtile(String title){
		List<Book> bookList = null;
		try{
			bookList = bookDao.queryByTitle(title);
			return bookList;
		}catch(EmptyException e){
			bookList = new ArrayList<>();
			return bookList;
		}
	}
	
	public List<Book> getBookListByCategoryID(int categoryID){
		List<Book> bookList = null;
		try{
			bookList = bookDao.queryByCategory(categoryID);
			return bookList;
		}catch(EmptyException e){
			bookList = new ArrayList<>();
			return bookList;
		}
	}
	
	public List<BookUserComment> getCommentByBookID(int bookID){
		List<BookUserComment> commentList = null;
		try{
			commentList = bookUserCommentDao.queryByBookID(bookID);
			return commentList;
		}catch(EmptyException e){
			commentList = new ArrayList<>();
			return commentList;
		}
	}
	
	private List<Book> paginate(List<Book> bookList, int page){
		int bookListSize = bookList.size();
		if(bookListSize <= booksEachPage){
			return bookList;
		}else{
			int fromIndex = (page-1) * booksEachPage;
			int toIndex = fromIndex+booksEachPage+1;
			return bookList.subList(fromIndex, toIndex);
		}
	}
	
	public List<Category> getCategoryList(){
		List<Category> categoryList = null;
		try{
			categoryList = categoryDao.queryAll();
		}catch(EmptyException e){
			categoryList = new ArrayList<>();
		}
		return categoryList;
	}
	
}
