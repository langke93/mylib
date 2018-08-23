package org.langke.spring.aop;

public interface BookService {
	public String OrderComputerMagazine(String userName, String bookName);

	public String OrderBook(String userName, String bookName);
}