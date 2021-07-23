package greatnetty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @description： 对象序列化成byte数组  编码
 * @author： Mr.He
 * @date： 2019-03-04 14:49
 **/
public class MyPackEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] bytes = messagePack.write(msg);//将对象序列成字节流
        out.writeBytes(bytes);//将字节流信息传入下一个ChannelHandler
    }
}
