package greatnetty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description： 服务端业务处理
 * @author： Mr.He
 * @date： 2019-02-26 10:27
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接···");
    }

    /**
     * 当通道有数据可读时执行
     *
     * @param ctx 上下文对象，可以从中取得相关联的 Pipeline、Channel、客户端地址等
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserInfo userInfo = (UserInfo) msg;
        System.out.println("服务端接收到数据：" + userInfo.toString() + "  count===" + count.incrementAndGet());

        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端,我接收到第【" + count.get() + "】信息了@", CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕后执行
     *
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端,我接收到第【"+count.get()+"】信息了@",CharsetUtil.UTF_8));
//        System.out.println("我是服务端,我接收到第【"+count.get()+"】信息了");
        // 接收完毕后 关闭客户端连接
        ctx.writeAndFlush(Unpooled.copiedBuffer("消息已经全部收到@",CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发生异常时执行
     *
     * @param ctx   上下文对象
     * @param cause 异常对象
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 关闭服务端的 Socket 连接
        ctx.close();
    }
}
