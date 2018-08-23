package org.langke.net.network.program;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressTest {
public static void main(String[] args) {
    try {
        InetAddress dnAddress =  InetAddress.getByName("video.woyo.com");
        System.out.println(dnAddress.getHostName());
        System.out.println(dnAddress.getHostAddress());
    } catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}
