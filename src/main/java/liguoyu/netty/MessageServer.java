package liguoyu.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by liguoyu@58.com on 2016/7/11.
 */
public class MessageServer {
    public static void main(String[] args){
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool()));

        bootstrap.getPipeline().addLast("decoder", new FrameDecoder() {
            @Override
            protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
                if (channelBuffer.readableBytes() < 4) {
                    return null;//(1)
                }
                int dataLength = channelBuffer.getInt(channelBuffer.readerIndex());
                if (channelBuffer.readableBytes() < dataLength + 4) {
                    return null;//(2)
                }

                channelBuffer.skipBytes(4);//(3)
                byte[] decoded = new byte[dataLength];
                channelBuffer.readBytes(decoded);
                String msg = new String(decoded);//(4)
                return msg;
            }
        });

        bootstrap.getPipeline().addLast("encode", new OneToOneEncoder() {
            @Override
            protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {
                if (!(o instanceof String)) {
                    return o;//(1)
                }
                String res = (String)o;
                byte[] data = res.getBytes();
                int dataLength = data.length;
                ChannelBuffer buf = ChannelBuffers.dynamicBuffer();//(2)
                buf.writeInt(dataLength);
                buf.writeBytes(data);
                return buf;
            }
        });

        bootstrap.getPipeline().addLast("handler",new SimpleChannelUpstreamHandler(){
            @Override
            public void messageReceived(
                    ChannelHandlerContext ctx, MessageEvent e) {
                e.getChannel().write(e.getMessage());
            }

            @Override
            public void exceptionCaught(
                    ChannelHandlerContext ctx, ExceptionEvent e) {
                System.out.println("Unexpected exception from downstream."+e.getCause());
                e.getChannel().close();
            }

            @Override
            public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
                    throws Exception {
                // TODO Auto-generated method stub

            }

            @Override
            public void channelConnected(ChannelHandlerContext ctx,
                                         ChannelStateEvent e) throws Exception {
                // TODO Auto-generated method stub
            }

        });
        bootstrap.bind(new InetSocketAddress(9550));
    }
}  
