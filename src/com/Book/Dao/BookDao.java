package com.Book.Dao;

import java.util.List;

import com.Book.Pojo.Book;

public interface BookDao {

	public boolean addBook(Book b);
	public boolean updateBook(Book b);
	public boolean deleteBook(Integer bookId);
	
	public Book searchBookById(Integer bookId);
	public List<Book> searchAllBook();
	public List<Book> searchBookByAuthor(String author);
	
}
