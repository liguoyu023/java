package liguoyu.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liguoyu@58.com on 2016/6/28.
 */
public class NIOClient {
    static int flag = 0;
    /*缓冲区大小*/
    private static int BLOCK = 4096;
    /*接受数据缓冲区*/
    private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
    /*发送数据缓冲区*/
    private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
    /*服务器端地址*/
    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(
            "localhost", 8888);

    public static void main(String[] args) throws IOException {
        send();
    }

    public static void send() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(SERVER_ADDRESS);

        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receiveText;
        String sendText;
        int count=0;


        while(true){
            selector.select();

            selectionKeys = selector.selectedKeys();
            iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                selectionKey = iterator.next();
                if(selectionKey.isConnectable()){
                    System.out.println("client connect");
                    client = (SocketChannel) selectionKey.channel();
                    if(client.isConnectionPending()){
                        client.finishConnect();
                        System.out.println("完成连接！");
                        sendbuffer.clear();
                        sendbuffer.put("Hello,Server".getBytes());
                        sendbuffer.flip();
                        client.write(sendbuffer);
                    }
                    client.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    client = (SocketChannel) selectionKey.channel();
                    receivebuffer.clear();
                    count = client.read(receivebuffer);
                    if(count>0){
                        receiveText = new String(receivebuffer.array(),0,count);
                        System.out.println("客户端接收服务端数据--"+receiveText);
                        client.register(selector,SelectionKey.OP_WRITE);
                    }
                }else if(selectionKey.isWritable()){
                    sendbuffer.clear();
                    client = (SocketChannel) selectionKey.channel();
                    sendText = "message from client--"+(flag++);
                    sendbuffer.put(sendText.getBytes());
                    sendbuffer.flip();
                    client.write(sendbuffer);
                    System.out.println("客户端向服务端发送数据--" + sendText);
                    client.register(selector,SelectionKey.OP_READ);
                }
            }
            selectionKeys.clear();

        }

    }

}  
