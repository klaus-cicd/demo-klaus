package com.demo.nettybase;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Klaus
 */
public class TestByteBuffer {
    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流; 2. RandomAccessFile
        try (FileChannel channel = new FileInputStream("classpath: hello.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            // 从缓冲区读取数据, 即写到缓冲区
            channel.read(buffer);

            // 打印buffer内容
            buffer.flip(); // 1. 先将buffer切换至读模式
            while (buffer.hasRemaining()) { // 检查Buffer内是否还有未读的数据
                byte b = buffer.get();
                // 强转为字符
                System.out.println((char) b);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
