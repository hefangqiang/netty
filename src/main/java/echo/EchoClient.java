package echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-24 19:36
 **/
public class EchoClient {
    private String host;
    private int post;

    public EchoClient(String host, int post) {
        this.host = host;
        this.post = post;
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.remoteAddress(host, post)    //配要连接的远程服务器地址
                    .group(eventLoopGroup)         //设置事件处理的线程池
                    .channel(NioSocketChannel.class)  //使用NIO进行通信
                    .handler(new EchoClientHandler()); //用于服务请求
            ChannelFuture channelFuture = bootstrap.connect().sync();//阻塞等待，直到连接成功
            channelFuture.channel().closeFuture().sync();//阻塞，直到channel关闭
        } catch (Exception e) {
            //关闭group
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) {
        try {
            new EchoClient("192.168.1.18", 7962).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
