package com.yltrcc.novel;

import org.apache.lucene.analysis.compound.hyphenation.TernaryTree;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.wltea.analyzer.core.IKSegmenter;

/**
 * 文件名称: IkAnalyzer<br>
 * 文件描述: IkAnalyzer demo<br>
 * 版权所有: jstarseven<br>
 * 完成日期: 2015-10-20 下午4:18:54<br>
 *
 * @param
 * @see
 * @since JDK1.6
 */
public class IKAnalyzerTest {

    static IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(""), true);

    public static void main(String[] args) throws IOException {
        // 创建一个线程池:
        ExecutorService es = new ThreadPoolExecutor(10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        String basePath = "E:\\BaiduSyncdisk\\小说\\测试\\";

        List<String> list = readFile02();
        HashMap<String, List<String>> map = new HashMap<>();
        int count = 0;
        for (String s : list) {
            List<String> ans = getIkWords(s);
            for (String an : ans) {
                if (map.get(an) == null) {
                    List<String> tmp = new ArrayList<>();
                    tmp.add(s);
                    map.put(an, tmp);
                }else {
                    List<String> value = map.get(an);
                    value.add(s);
                    map.put(an,value);
                }
            }
            System.out.println("处理第" + ++count + "行位置数据");
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            es.submit(new DownLoadThread(basePath + entry.getKey() + ".txt", entry.getValue()));
        }

        // 关闭线程池:
        es.shutdown();
    }


    public static List<String> getIkWords(String sentence){
        List<String> ans = new ArrayList<>();
        try (StringReader stringReader = new StringReader(sentence)) {
            ikSegmenter.reset(stringReader);

            while (true) {
                Lexeme next = ikSegmenter.next();//获取数据
                if (next == null) break;//如果分词完成,跳出循环
                String word = next.getLexemeText();//获取分词内容
                ans.add(word);
                //System.out.println(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ans;
    }

    public static class DownLoadThread extends Thread {

        private final String targetPath;

        private final List<String> appendContent;

        public DownLoadThread(String targetPath, List<String> appendContent) {
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
                        //System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒,当前线程名为：" + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    }
                }
                //随机写文件的时候从哪个位置开始写
                raFile.seek(raFile.length());
                for (String s : appendContent) {
                    s = s+"\n";
                    raFile.write(s.getBytes());
                }

                //System.out.println("线程" + Thread.currentThread().getName() + "下载完毕");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 一行一行读取文件，解决读取中文字符时出现乱码
     * <p>
     * 流的关闭顺序：先打开的后关，后打开的先关，
     * 否则有可能出现java.io.IOException: Stream closed异常
     *
     * @throws IOException
     */
    public static List<String> readFile02() throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Files.newInputStream(
                        Paths.get("E:\\BaiduSyncdisk\\小说\\12.txt")), StandardCharsets.UTF_8));
             ) {
            String line = "";
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }
}