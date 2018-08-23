package org.langke.net.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

public class ClientTest {
	public static void main(String args[]) throws  Exception{
		String url = "http://hessian.caucho.com/test/test";

		HessianProxyFactory factory = new HessianProxyFactory();
		BasicAPI basic = (BasicAPI) factory.create(BasicAPI.class, url);
		System.out.println("hello(): " + basic.hello());

	}
}
