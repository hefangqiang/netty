package bio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-02-28 14:25
 **/
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9099);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            inputStream.read(b);


            System.out.println("服务端接收到数据：" + new String(b));


            socket.getOutputStream().write("你好客户端".getBytes());
        }


    }
}
