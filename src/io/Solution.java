package io;

import java.io.*;

public class Solution {
    private static final int COUNT = 5000;
    private static final String MSG = "/Library/Java/JavaVirtualMachines/";
    public static void main(String[] args) {
        Solution solution = new Solution();
        try {
            solution.byteStream();
            solution.byteStreamBuffered();
            solution.charStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 字节流，不用到缓冲区
    public void byteStream() throws IOException {
        String path = "/Users/Jason/eclipse-workspace/playground/src/javabase/io/byte.txt";
        String msg = MSG;
        OutputStream outputStream = new FileOutputStream(path);
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            outputStream.write(msg.getBytes());
        }
        System.out.println(String.format("字节流，不用到缓冲区，耗时：%d毫秒", System.currentTimeMillis() - start));
    }
    /**
     * 字节流，用缓冲区
     * 每次调用 write() 方法并不会产生实际的 write 系统调用，
     * 而是会先将数据存放于 BufferedOutputStream
     * 实例内部的缓冲区中（缓冲区默认大小 8 KB），
     * 等缓冲区满、或者手动调用 BufferedOutputStream.flush() 或
     * close() 方法时，才会真正调用 write
     * 系统调用将缓冲区数据写入 page cache,
     * 这样就比不带缓冲区的 IO 少了很多次的系统调用，性能自然就大大提升了。
     *
     * @exception  IOException  if file not found.
     *
     * **/
    public void byteStreamBuffered() throws IOException {
        String path = "/Users/Jason/eclipse-workspace/playground/src/javabase/io/byte3.txt";
        String msg = MSG;
        OutputStream outputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            bufferedOutputStream.write(msg.getBytes());
        }
        bufferedOutputStream.close();
        System.out.println(String.format("字节流，用缓冲区，耗时：%d毫秒", System.currentTimeMillis() - start));
    }

    public void charStream() throws IOException {

        String path = "/Users/Jason/eclipse-workspace/playground/src/javabase/io/byte2.txt";
        String msg = MSG;
        OutputStreamWriter outputStreamWriter = new FileWriter(path);
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            outputStreamWriter.write(msg);
        }
        outputStreamWriter.close();
        System.out.println(String.format("字符流，用缓冲区，耗时：%d毫秒", System.currentTimeMillis() - start));
    }
}
