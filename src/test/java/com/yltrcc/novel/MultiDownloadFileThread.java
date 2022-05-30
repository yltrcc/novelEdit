package com.yltrcc.novel;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.*;

/**
 * @ProjectName: emp_customer
 * @Package: PACKAGE_NAME
 * @ClassName: MultiDownloadFileThread
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/11 15:03
 * @Version: 1.0
 */
public class MultiDownloadFileThread {


    public static void main(String[] args) {
        // 创建一个线程池:
        ExecutorService es = new ThreadPoolExecutor(10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 100; i++) {
            es.submit(new DownLoadThread("D:\\1.txt", "内容:" + i));
        }
        // 关闭线程池:
        es.shutdown();
    }


    public static class DownLoadThread extends Thread {

        private final String targetPath;

        private final String appendContent;

        public DownLoadThread(String targetPath, String appendContent) {
            this.appendContent = appendContent;
            this.targetPath = targetPath;
        }

        @Override
        public void run() {
            FileLock flin = null;

            try(RandomAccessFile raFile = new RandomAccessFile(targetPath, "rwd");
                FileChannel fcin = raFile.getChannel();) {

                while (true) {
                    try {
                        flin = fcin.tryLock();
                        break;
                    } catch (Exception e) {
                        System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒,当前线程名为：" + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    }
                }
                //随机写文件的时候从哪个位置开始写
                raFile.seek(raFile.length());
                raFile.write(appendContent.getBytes());

                System.out.println("线程" + Thread.currentThread().getName() + "下载完毕");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}