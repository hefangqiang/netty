package greatnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-26 10:27
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接···");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到客户端信息");
        UserInfo userInfo = (UserInfo) msg;
        System.out.println("服务端接收到数据："+userInfo.toString()+"  count==="+count.incrementAndGet());

        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端,我接收到第【"+count.get()+"】信息了@",CharsetUtil.UTF_8));
        System.out.println("我是服务端,我接收到第【"+count.get()+"】信息了");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       // ctx.write("").addListener(ChannelFutureListener.CLOSE);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端,我接收到第【"+count.get()+"】信息了@",CharsetUtil.UTF_8));
//        System.out.println("我是服务端,我接收到第【"+count.get()+"】信息了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
