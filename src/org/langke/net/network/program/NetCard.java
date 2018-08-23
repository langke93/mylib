package org.langke.net.network.program;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetCard {
	public static void main(String[] args) {
		System.out.println("\n+---------------------[ Network Cards ]----------------------+\n");
		try {
			for (Enumeration list = NetworkInterface.getNetworkInterfaces(); list.hasMoreElements();) {
				NetworkInterface iFace = (NetworkInterface) list.nextElement();
				System.out.println("Card:" + iFace.getDisplayName());
				InetAddress adr = null;
				for (Enumeration addresses = iFace.getInetAddresses(); addresses.hasMoreElements(); )
					adr = (InetAddress) addresses.nextElement();
					System.out.println(" -> "	+ adr.getAddress());

					System.out.println(" -> "	+ adr.getHostAddress());
			}
			 
		} catch (SocketException se) {
			System.out.println("Failed discovering network cards!");
			System.out.println("Error: " + se);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
