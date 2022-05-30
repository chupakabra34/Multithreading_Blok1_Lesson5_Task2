import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: Prekrasnov Sergei
 * \* Date: 30.05.2022
 * \* ----- group JAVA-27 -----
 * \*
 * \* Description:  Домашнее задание к занятию 1.5 Клиент-серверное взаимодействие. Blocking и Non-Blocking IO.
 * \*
 * \* Задача 2. Долой пробелы
 * \
 */
public class Client {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",
                23334);
        final SocketChannel socketChannel = SocketChannel.open();
        try (socketChannel; Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(socketAddress);
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String msg;
            while (true) {
                System.out.println("Введите строку с пробелами... (Для завершения работы введите \"end\")");
                msg = scanner.nextLine();
                if ("end".equals(msg)) break;
                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount,
                        StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}