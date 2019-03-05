package fristNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-26 10:27
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private int  count=0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("服务端接收到数据："+((ByteBuf)msg).toString(CharsetUtil.UTF_8));
        System.out.println("count==="+count++);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端",CharsetUtil.UTF_8));
        System.out.println("服务端发送数据：你好，客户端");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}
