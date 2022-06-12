package com.yltrcc.novel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoveDuplicateTest {

    private static AtomicInteger atomicCount;

    public static void main(String[] args) throws IOException, InterruptedException {
        // 创建一个线程池:
        ExecutorService es = new ThreadPoolExecutor(10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        atomicCount = new AtomicInteger(0);
        ArrayList<String> items = getItems();
        int size = items.size();
        //iterator遍历
        Iterator<String> iterator = items.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            HashSet<String> hset = readFile02("E:\\BaiduSyncdisk\\小说\\前缀测试\\" + next + ".txt");
            es.submit(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        writeFile01(hset.toArray(), "E:\\BaiduSyncdisk\\小说\\前缀测试\\" + next + ".txt");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    atomicCount.addAndGet(1);
                }
            }));
            iterator.remove();
        }
        int countTmp = 0;
        do {
            if (atomicCount.get() != countTmp) {
                countTmp = atomicCount.get();
                System.out.println("当前进度为：" + countTmp * 1.0 / size * 100 + "%");
            }
            Thread.sleep(2000);
        } while (countTmp != size);
        // 关闭线程池:
        es.shutdown();
    }

    /**
     * 一行一行读取文件，解决读取中文字符时出现乱码
     * <p>
     * 流的关闭顺序：先打开的后关，后打开的先关，
     * 否则有可能出现java.io.IOException: Stream closed异常
     *
     * @throws IOException
     */
    public static HashSet<String> readFile02(String path) throws IOException {
        HashSet<String> hset = new HashSet<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Files.newInputStream(
                        Paths.get(path)), StandardCharsets.UTF_8));
        ) {
            String line = "";
            while ((line = br.readLine()) != null) {
                hset.add(line.replaceAll("\n|\t",""));
            }
        }
        return hset;
    }

    /**
     * 读取文件名 作为索引
     */
    public static ArrayList<String> getItems() throws FileNotFoundException {
        ArrayList<String> ans = new ArrayList<>();
        File file = new File("E:\\BaiduSyncdisk\\小说\\前缀测试");
        File[] files = file.listFiles();
        if (files == null) {
            throw new FileNotFoundException();
        }
        for (File tmpFile:files) {
            ans.add(tmpFile.getName().replace(".txt", ""));
        }
        return ans;
    }

    /**
     * 一行一行写入文件，适合字符写入，若写入中文字符时会出现乱码
     * <p>
     * 流的关闭顺序：先打开的后关，后打开的先关，
     * 否则有可能出现java.io.IOException: Stream closed异常
     *
     * @throws IOException
     */
    public static void writeFile01(Object[] arrs, String path) throws IOException {

        FileWriter fw = new FileWriter(new File(path));
        //写入中文字符时会出现乱码
        BufferedWriter bw = new BufferedWriter(fw);
        //BufferedWriter  bw=new BufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8")));

        for (Object arr : arrs) {
            bw.write(arr + "\n");
        }
        bw.close();
        fw.close();
    }
}
