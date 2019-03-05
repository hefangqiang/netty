package greatnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-26 10:17
 **/
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接到服务器，开始发送数据");
        UserInfo userInfo;
        for (int i = 0; i <10 ; i++) {
            userInfo = new UserInfo("user"+i,"Man",18);
            ctx.write(userInfo);
            System.out.println("---客户端发送数据："+userInfo.toString()+"---");
        }
        ctx.flush();
        System.out.println("发送完毕");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("客户端接收到："+msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
