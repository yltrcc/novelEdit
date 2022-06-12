package com.yltrcc.novel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RemoveDuplicateTest {

    public static void main(String[] args) throws IOException {
        ArrayList<String> items = getItems();
        for (String s: items) {
            HashSet<String> hset = readFile02("E:\\BaiduSyncdisk\\小说\\前缀测试\\" + s + ".txt");
            writeFile01(hset.toArray(), "E:\\BaiduSyncdisk\\小说\\前缀测试\\" + s + ".txt");
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
