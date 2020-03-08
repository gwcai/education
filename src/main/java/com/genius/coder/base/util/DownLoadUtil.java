package com.genius.coder.base.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
public class DownLoadUtil {
    private DownLoadUtil() {
    }

    public static HttpServletResponse download(String path, HttpServletResponse response, String filename) throws IOException {
        File file = new File(path);
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"");
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        return response;
    }

    public static void download(ByteArrayOutputStream outputStream, HttpServletResponse response, String filename) throws IOException {
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"");
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(outputStream.toByteArray());
        toClient.flush();
        outputStream.close();
        toClient.close();
    }

    public static void flushToResponse(ByteArrayOutputStream outputStream, HttpServletResponse response, String contentType) throws IOException {
        response.reset();
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setStatus(200);
        response.setContentType(contentType == null ? "application/pdf;charset=UTF-8" : contentType);
        toClient.write(outputStream.toByteArray());
        toClient.flush();
        outputStream.close();
        toClient.close();
    }
}

