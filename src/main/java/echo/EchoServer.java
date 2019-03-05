package echo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-24 20:38
 **/
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    private void start() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.localAddress(port)       //设置服务器端口号
                .group(eventLoopGroup)         //设置事件处理线程池
                .channel(NioServerSocketChannel.class)  //使用NIO通信
                //接收到连接请求，新启一个socket通信，也就是channel，每个channel
                //有自己的事件handler
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                });
            //绑定一个端口  阻塞等待，直到连接完成(客户端连接到服务器)
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            //阻塞，直到channel关闭(客户端断开连接)
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        System.out.println("服务器端开始启动。。。");
        new EchoServer(7962).start();
        System.out.println("服务器关闭");
    }
}
