package echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-24 19:43
 **/
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    //客户端检测到channel活跃后做的事情(连接到服务器端后开始做得事情)
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端向服务器端发送数据："+"你好，服务端");
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，服务端",CharsetUtil.UTF_8));
    }

    //读到服务器端发送的数据后的处理
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("客户端接收到服务器信息："+msg.toString(CharsetUtil.UTF_8));
    }

    //出现异常时处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
