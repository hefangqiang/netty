package fristNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

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
        final ServerHandler serverHandler1 = new ServerHandler();
        final ServerHandler2 serverHandler2 = new ServerHandler2();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler1);
                            ch.pipeline().addLast(serverHandler2);
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
