package liguoyu.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liguoyu@58.com on 2016/6/28.
 */
public class NIOServer {

    /*标识数字*/
    private  int flag = 0;
    /*缓冲区大小*/
    private  int BLOCK = 4096;
    /*接受数据缓冲区*/
    private  ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
    /*发送数据缓冲区*/
    private  ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);

    private Selector selector;
    public NIOServer(int port) throws IOException{

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));

        selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start-----"+port);
    }

    private void listen() throws IOException {
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                //处理
                handleKey(selectionKey);
            }
        }
    }

    private void handleKey(SelectionKey selectionKey) throws IOException{
        ServerSocketChannel server = null;
        SocketChannel client = null;
        String receiveText;
        String sendText;
        int count=0;
        if(selectionKey.isAcceptable()){
            server = (ServerSocketChannel)selectionKey.channel();
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector,SelectionKey.OP_READ);
        }else if(selectionKey.isReadable()){
            client = (SocketChannel)selectionKey.channel();
            receivebuffer.clear();
            count = client.read(receivebuffer);
            if(count>0){
                receiveText = new String(receivebuffer.array(),0,count);
                System.out.println("接收到数据----"+receiveText);
                client.register(selector, SelectionKey.OP_WRITE);
            }
        }else if(selectionKey.isWritable()){
            sendbuffer.clear();
            client = (SocketChannel)selectionKey.channel();
            sendText = "message from server--"+(flag++);
            sendbuffer.put(sendText.getBytes());
            sendbuffer.flip();
            client.write(sendbuffer);
            System.out.println("服务端向客户端发送数据--" + sendText);
            client.register(selector,SelectionKey.OP_READ);
        }
    }






    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer(8888);
        server.listen();
    }



}  
