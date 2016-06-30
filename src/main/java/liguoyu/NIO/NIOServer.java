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

    /**
     * 构造函数 主要是为了开启套接字 并注册到selector
     * @param port
     * @throws IOException
     */
    public NIOServer(int port) throws IOException{
        //通过Selector中的SelectorProvider创建一个ServerSocketChannel
        //Windows下借用了pipe管道 linux下是epoll
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置成非阻塞 最底层是一个native的方法 调用了最底层的IOUtil来实现了非阻塞的效果
        serverSocketChannel.configureBlocking(false);
        //使用单例模式开启了一个套接字
        ServerSocket serverSocket = serverSocketChannel.socket();
        //绑定端口号
        serverSocket.bind(new InetSocketAddress(port));
        //获取对应的selector
        selector = Selector.open();
        //把channel注册到selector上 默认为接受状态
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start-----"+port);
    }

    /**
     * 监听
     * @throws IOException
     */
    private void listen() throws IOException {
        while (true){
            selector.select();
            //获取现在已经注册的动作
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //迭代并处理
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
