package greatnetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-03-04 15:06
 **/
public class MyPackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
       int len = msg.readableBytes();//获取ByteBuf可读的字节
       byte[] bytes = new byte[len];
       msg.getBytes(msg.readerIndex(),bytes,0,len);//将msg 读取到bytes字节数组
       MessagePack messagePack = new MessagePack();
       out.add(messagePack.read(bytes, UserInfo.class));//将bytes数组信息反序列化成UserInfo对象传递给下一个channelHandler
    }


}
