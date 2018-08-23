package org.jboss.netty.demo.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
 
@ChannelPipelineCoverage("all")
public class MessageServerHandler extends SimpleChannelUpstreamHandler { 
    private static final Logger logger = Logger.getLogger(
            MessageServerHandler.class.getName());
 
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) {
        if (!(e.getMessage() instanceof String)) {
            return;//(1)
        }
        String msg = (String) e.getMessage();
        System.err.println("got msg:"+msg);
        e.getChannel().write(msg);//(2)
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.",
                e.getCause());
        e.getChannel().close();
    }
}