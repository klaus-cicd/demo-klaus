package com.demo.nettybase;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ByteBuffer简介
 *
 * @author Klaus
 */
@Slf4j
public class BufferAndChannelDemo01 {

    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("hello.txt", "rw")) {
            // 获取文件流通道
            FileChannel channel = file.getChannel();
            // 准备一个指定长度的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            do {
                // 从channel内读取数据到buffer
                int len = channel.read(buffer);
                log.info("len = {}", len);
                if (len == -1) {
                    break;
                }
                // 将buffer切换至读模式
                buffer.flip();
                // 检查buffer内是否还有未读的数据
                while (buffer.hasRemaining()) {
                    log.info("{}", buffer.get());
                }
                buffer.clear();
            } while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
