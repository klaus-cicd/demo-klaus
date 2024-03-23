package com.silas.demo.helloworld.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip跑龙套
 *
 * @author klaus
 * @date 2024/01/03
 */
public class GzipUtil {


    public static byte[] decompress(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        while ((len = gzip.read(buffer)) > 0) {
            bos.write(buffer, 0, len);
        }
        gzip.close();
        return bos.toByteArray();
    }


    public static byte[] compress(String json) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(json.getBytes());
        gzip.close();
        return bos.toByteArray();
    }
}
