package org.jboss.netty.demo.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class MessageServer {
    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());
    private static final int port = 8080;
    public static void main(String[] args) throws Exception {
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                        )
                );
 
        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new MessageServerPipelineFactory());
 
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(port));
        logger.log(Level.INFO, "bind address:"+port);
    }
}