package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-28 14:16
 **/
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9099);
        byte[] b = "你好，服务端".getBytes();
        socket.getOutputStream().write(b);
        socket.getOutputStream().flush();


        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        inputStream.read(bytes, 0, 1024);
        String s = new String(bytes);
        System.out.println("客户端接收到数据：" + s);

    }
}
