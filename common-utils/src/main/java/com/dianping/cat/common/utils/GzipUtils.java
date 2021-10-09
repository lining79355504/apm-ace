package com.dianping.cat.common.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author mort
 * @Description
 * @date 2021/10/10
 **/
public class GzipUtils {

    private static int BUFFER = 1024 * 32;

//    public static byte[] compress(byte[] bytes) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 32);
//        GZIPOutputStream gzip = new GZIPOutputStream(out);
//        gzip.write(bytes);
//        gzip.close();
//        byte[] output = out.toByteArray();
//        out.flush();
//        out.close();
//        return output;
//    }


    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 压缩
        compress(bais, baos);

        byte[] output = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return output;
    }


    /**
     * 数据压缩
     *
     * @param is
     * @param os
     * @throws IOException
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os) throws IOException {

        GZIPOutputStream gos = new GZIPOutputStream(os);

        int count;
        byte data[] = new byte[BUFFER];
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            gos.write(data, 0, count);
        }

        gos.finish();

        gos.flush();
        gos.close();
    }


    public static byte[] decompress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 解压缩

        decompress(bais, baos);

        data = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return data;
    }


    /**
     * 数据解压缩
     *
     * @param is
     * @param os
     * @throws IOException
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os) throws IOException {

        GZIPInputStream gis = new GZIPInputStream(is);

        int count;
        byte[] data = new byte[BUFFER];
        while ((count = gis.read(data, 0, BUFFER)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();
    }
}