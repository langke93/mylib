package org.langke.spring.aop;

public class BookServiceImpl implements BookService {
	public String OrderBook(String name, String bookName) {
		String result = null;
		result = "订购" + bookName + "成功";
		return result;
	}

	public String OrderComputerMagazine(String userName, String bookName) {
		String result = null;
		result = "订购" + bookName + "成功";
		return result;
	}
}