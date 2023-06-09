package com.Book.Test;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Book.Dao.BookDaoImpl;
import com.Book.Pojo.Book;


public class BookTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Integer bookId;
		String bookName;
		String author;
		Scanner sc=new Scanner(System.in);

		ApplicationContext context=new ClassPathXmlApplicationContext("ApplicationContext.xml");
		BookDaoImpl bimpl=(BookDaoImpl)context.getBean("bimpl");
		Book b;
		List<Book> blist;
		Boolean flag;
		while(true) {
			System.out.println("1---> Add Book");
			System.out.println("2---> Show all books");
			System.out.println("3---> Update book");
			System.out.println("4---> Delete Book");
			System.out.println("5---> Search Book by Id");
			System.out.println("6---> Search Book by Author");

			int option=sc.nextInt();
			sc.nextLine();

			switch(option) {
			case 1 :
				System.out.println("Enter book name :");
				bookName=sc.nextLine();

				System.out.println("Enter Author :");
				author=sc.nextLine();
				b=(Book)context.getBean("b");
				b.setAuthor(author);
				b.setBookName(bookName);
				flag=bimpl.addBook(b);
				if(flag)
					System.out.println("Book added successefully....");
				else
					System.out.println("Error while adding..");
				break;

			case 2 :
				blist=bimpl.searchAllBook();
				if(blist!=null && blist.isEmpty()!=true) {
					for(Book b1 : blist) {
						System.out.println("Name : "+b1.getBookName());
						System.out.println("Author : "+b1.getAuthor());
						System.out.println();
					}
				}
				else
					System.out.println("Book table is empty..");
				break;

			case 3 :
				System.out.println("Enter Book Id to update :");
				bookId=sc.nextInt();
				sc.nextLine();
				b=bimpl.searchBookById(bookId);
				if(b==null)
					System.out.println("No book found with this id");
				else {
					System.out.println("The book you want to update");
					boolean exit=false;
					while(exit==false) {
						System.out.println("1---> update book name");
						System.out.println("2---> update author");
						System.out.println("3---> dont want to update");

						int ans=sc.nextInt();
						sc.nextLine();

						switch(ans) {
						case 1 :
							System.out.println("Enter book name :");
							bookName=sc.nextLine();

							b.setBookName(bookName);
							flag=bimpl.updateBook(b);
							if(flag)
								System.out.println("Book updated successefully....");
							else
								System.out.println("Error while updating..");
							break;

						case 2 :
							System.out.println("Enter author name :");
							author=sc.nextLine();

							b.setAuthor(author);
							flag=bimpl.updateBook(b);
							if(flag)
								System.out.println("author updated successefully....");
							else
								System.out.println("Error while updating..");
							break;

						case 3 :
							exit=true;
							break;
						}
					}
				}
				break;

			case 4 :
				System.out.println("Enter Book Id to get deleted :");
				bookId=sc.nextInt();
				sc.nextLine();
				flag=bimpl.deleteBook(bookId);
				if(flag)
					System.out.println("Book deleted successefully");
				else
					System.out.println("Error while updating..");
				break;

			case 5 :
				System.out.println("Enter Book Id :");
				bookId=sc.nextInt();
				sc.nextLine();
				b=bimpl.searchBookById(bookId);
				if(b==null)
					System.out.println("No book found with this id");
				else {
				System.out.println("Name : "+b.getBookName());
				System.out.println("Author : "+b.getAuthor());
				}
				break;

			case 6 :
				System.out.println("Enter Book Author :");
				author=sc.nextLine();
				blist=bimpl.searchBookByAuthor(author);
				if(blist!=null && blist.isEmpty()!=true) {
					for(Book b1 : blist) {
						System.out.println("Name : "+b1.getBookName());
						System.out.println("Author : "+b1.getAuthor());
						System.out.println();
					}
				}
				else
					System.out.println("Book table is empty..");
				break;
			}
		}
	}

}
