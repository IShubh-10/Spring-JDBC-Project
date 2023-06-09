package com.Book.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.Book.Pojo.Book;

public class BookDaoImpl implements BookDao {

	private JdbcTemplate template;
	String sql;
	private NamedParameterJdbcTemplate namedTemplate;

	public NamedParameterJdbcTemplate getNamedTemplate() {
		return namedTemplate;
	}
	public void setNamedTemplate(NamedParameterJdbcTemplate namedTemplate) {
		this.namedTemplate = namedTemplate;
	}
	public JdbcTemplate getTemplate() {
		return template;
	}
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}


	@Override
	public boolean addBook(Book b) {
		sql="insert into Book(bookName, author) values ('"+b.getBookName()+"','"+b.getAuthor()+"')";
		/*
		 * PreparedStatementCallback<Boolean> psc=new
		 * PreparedStatementCallback<Boolean>() {
		 * 
		 * @Override public Boolean doInPreparedStatement(PreparedStatement ps) throws
		 * SQLException, DataAccessException { ps.setString(1, b.getBookName());
		 * ps.setString(2, b.getAuthor()); int i=ps.executeUpdate(); if(i>0) return
		 * true; else return false; } }; return template.execute(sql, psc);
		 */
		int i=template.update(sql);
		if(i>0)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateBook(Book b) {
		sql="update Book set bookName=?, author=? where bookId=?";
		PreparedStatementCallback<Boolean> psc=new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, b.getBookName());
				ps.setString(2, b.getAuthor());
				ps.setInt(3, b.getBookId());
				int i=ps.executeUpdate();
				if(i>0)
					return true;
				else
					return false;
			}
		};
		return template.execute(sql, psc);
	}

	@Override
	public boolean deleteBook(Integer bookId) {
		/*
		 * sql="delete from Book where bookId="+bookId; int i=template.update(sql);
		 * if(i>0) return true; else return false;
		 */

		sql="delete from Book where bookId=:bid";
		Map<String, Object> map=new HashMap<>();
		map.put("bid", bookId);

		PreparedStatementCallback<Boolean> psc=new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				int i=ps.executeUpdate();
				if(i>0) 
					return true;
				else 
					return false;
			}
		};
		return namedTemplate.execute(sql, map, psc);
	}

	@Override
	public Book searchBookById(Integer bookId) {
		sql="select * from Book where bookId="+bookId;
		ResultSetExtractor<Book> rse=new ResultSetExtractor<Book>() {

			@Override
			public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					Book b=new Book();
					b.setBookId(rs.getInt(1));
					b.setBookName(rs.getString(2));
					b.setAuthor(rs.getString(3));

					return b;
				}
				return null;
			}
		};
		return template.query(sql, rse);
	}

	@Override
	public List<Book> searchAllBook() {
		sql="select * from Book";
		/*
		 * ResultSetExtractor<List<Book>> rse=new ResultSetExtractor<List<Book>>() {
		 * 
		 * @Override public List<Book> extractData(ResultSet rs) throws SQLException,
		 * DataAccessException {
		 * 
		 * List<Book> blist=new ArrayList<>(); while(rs.next()) { Book b=new Book();
		 * b.setBookId(rs.getInt(1)); b.setBookName(rs.getString(2));
		 * b.setAuthor(rs.getString(3));
		 * 
		 * blist.add(b); } return blist; } }; return template.query(sql, rse);
		 */

		RowMapper<Book> rm=new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int row) throws SQLException {
				Book b=new Book();
				b.setBookId(rs.getInt(1));
				b.setBookName(rs.getString(2));
				b.setAuthor(rs.getString(3));

				return b;
			}
		};
		return template.query(sql, rm);
	}

	@Override
	public List<Book> searchBookByAuthor(String author) {
		sql="select * from Book where author like'%"+author+"%'";
		RowMapper<Book> rm=new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int row) throws SQLException {
				Book b=new Book();
				b.setBookId(rs.getInt(1));
				b.setBookName(rs.getString(2));
				b.setAuthor(rs.getString(3));

				return b;
			}
		};
		return template.query(sql, rm);
	}

}
