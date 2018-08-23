package org.langke.net.webserver;

import javax.net.ServerSocketFactory;
import javax.net.ssl.*;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.awt.event.*;
import java.security.KeyStore;

public class HttpsServer extends java.awt.Frame 
{
	private int iPort = 80;	//default port
	public static String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webroot";	//default web root
    String keystore = "serverKeys";
    char keystorepass[] = "doonedoone".toCharArray();
    char keypassword[] = "doonedoone".toCharArray();

    // The port number which the server will be listening on
    public static final int HTTPS_PORT = 443;
	public HttpsServer()
	{
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		getConfig();
		this.start();
	}

	public static void main(String[] args)  {
    	HttpsServer server = new HttpsServer();
	}

	private void getConfig() {	//get web root directory and port from file "config.ini"
		int BUFFER_SIZE = 1024;
	    byte[] bytes = new byte[BUFFER_SIZE];
	    FileInputStream fis = null;
	    try {
	      File file = new File("config.ini");
	      if (file.exists()) {
	        fis = new FileInputStream(file);
	        int ch = fis.read(bytes, 0, BUFFER_SIZE);
	        if (ch!=-1) {
	        	String config = new String(bytes);
				int webRootIndex = config.indexOf("webroot: ");
				if (webRootIndex != -1) 
					WEB_ROOT = config.substring(webRootIndex + 9, config.indexOf('\n',webRootIndex)).trim();
				if (WEB_ROOT.equals("")) WEB_ROOT = "webroot";	//default web root
				WEB_ROOT = System.getProperty("user.dir") + File.separator  + WEB_ROOT;
				System.out.println("web root:   " + WEB_ROOT);
					
				int portIndex = config.indexOf("port: ");
				if (portIndex != -1) 
					iPort = Integer.parseInt(config.substring(portIndex + 6, config.indexOf('\n',portIndex)).trim());
				if (iPort == 0) iPort = 80;	//default port
				System.out.println("port:       " + String.valueOf(iPort));
				System.out.println("");
	        }
	      }
	      else {
	        // file not found
	        System.out.println("config file not found.");
	        System.exit(1);
	      }
	    }
	    catch (Exception e) {
	      // thrown if cannot instantiate a File object
	      System.out.println(e.toString() );
	    }
	    finally {
	      if (fis!=null)
	      	try{
	        	fis.close();
	     	}
	     	catch (Exception e){}
	    }
	}

	public void start() {
		System.out.println("web sever starting...");
		SSLServerSocket serverSocket = null;
    	try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keystore), keystorepass);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keypassword);
            SSLContext sslcontext = SSLContext.getInstance("SSLv3");
            sslcontext.init(kmf.getKeyManagers(), null, null);
            ServerSocketFactory ssf = sslcontext.getServerSocketFactory();
            serverSocket = (SSLServerSocket)ssf.createServerSocket(HTTPS_PORT);

    		//SSLServerSocketFactory ssf = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			//serverSocket =  ssf.createServerSocket(iPort);
    	}
    	catch (Exception e) {
      		e.printStackTrace();
      		System.exit(1);
    	}

		System.out.println("web sever started");
		System.out.println("");
	
	    // Loop waiting for a request
		while (true) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();

				// create Request object and parse
				Request request = new Request(input);
				request.parse();

				// create Response object
				Response response = new Response(output);
        		response.setRequest(request);
        		response.sendStaticResource();

        		// Close the socket
        		socket.close();
      		}
      		catch (Exception e) {
        		e.printStackTrace();
        		continue;
      		}
    	}
  	}
}
