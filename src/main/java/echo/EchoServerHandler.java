package echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-24 20:55
 **/
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    //服务端读到网络数据后，做的处理
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器端接受到客户端信息："+byteBuf.toString(CharsetUtil.UTF_8));
        //数据可能没有全部读完
        //ctx.write(byteBuf);
    }

    //服务端读完网络数据后，做的处理
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端",CharsetUtil.UTF_8))
               .addListener(ChannelFutureListener.CLOSE);//flush完后关闭连接
    }


    //发生异常，处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
