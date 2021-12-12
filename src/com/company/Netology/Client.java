package com.company.Netology;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 23334);
        final SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(inetSocketAddress);
        try(Scanner sc = new Scanner(System.in)) {

            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            String msg;
            while (true) {
                System.out.println("Connection is established\n" +
                        "Введите сообщение, из которого нужно удалить пробелы, или 'end' для выхода");
                msg = sc.nextLine();

                if("end".equals(msg)) break;

                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));

                int bytesCount = socketChannel.read(inputBuffer);
                String editMsg = new String(inputBuffer.array(), 0, bytesCount);
                System.out.println(editMsg);
                inputBuffer.clear();
            }
        } catch (IIOException e) {
            e.printStackTrace();
        } finally {
            socketChannel.close();
        }
    }
}
