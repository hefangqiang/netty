package greatnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-26 10:14
 **/
public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    private void start() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                                65535,0,2,
                                0,2));//根据报文头中的长度获取要读的消息大小
                        ch.pipeline().addLast(new MyPackDecoder());//解码  将byte消息反序列化成UserInfo对象
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        System.out.println("开始启动服务端。。。");
       new Server(9672).start();
        System.out.println("服务端关闭");
    }
}
