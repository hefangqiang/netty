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
        // 创建 BossGroup 和 WorkerGroup
        // 1. bossGroup 只处理连接请求
        // 2. workerGroup 处理业务操作和读写操作
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务器端的启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 配置参数
            serverBootstrap
                    // 设置线程组
                    .group(bossGroup, workerGroup)
                    // 设置服务器端通道类型
                    .channel(NioServerSocketChannel.class)
                    // 设置要绑定和监听的端口
                    .localAddress(port)
                    // 添加channel处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                                    65535, 0, 2,
                                    0, 2));//根据报文头中的长度获取要读的消息大小
                            ch.pipeline().addLast(new MyPackDecoder());//解码  将byte消息反序列化成UserInfo对象
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            // 绑定端口，启动服务器，生成一个 channelFuture 对象
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println("服务器启动完成。。。");
            // 对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        System.out.println("启动服务端。。。");
        new Server(9672).start();
        System.out.println("服务端关闭");
    }
}
