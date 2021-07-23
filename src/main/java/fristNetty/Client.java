package fristNetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-26 10:13
 **/
public class Client {
    private int port;
    private String host;

    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }

    private void start() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.remoteAddress(host, port)
                    .group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            eventLoopGroup.shutdownGracefully();
        }


    }


    public static void main(String[] args) {
        new Client("127.0.0.1", 9672).start();
    }
}
