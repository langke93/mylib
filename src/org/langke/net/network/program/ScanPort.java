package org.langke.net.network.program;
import java.net.*;
import java.io.*;
/**
*多线程扫描端口程序:java scanport 127.0.0.1
*/
public class ScanPort {
	public static void main(String args[])
	{
		String ip = "test.77160.com";
		for(int i = 1000; i <= 6000; i++)
			{
				new MultiThread(ip,i).start();
			}
	}
}

class MultiThread extends Thread
{
	private String args;
	private int port;
	MultiThread(String args,int port)
	{
		this.args = args;
		this.port = port;
	}
	public void run()
	{
		try{
			Socket s = new Socket(args,port);
			System.out.println("连接端口: " + port + " 成功!");
			s.close();
		} catch(IOException e){
	//	System.out.println(e.getMessage());
		
		}
	}
}
