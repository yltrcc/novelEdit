package com.yltrcc.novel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    /**
     * 一行一行读取文件，解决读取中文字符时出现乱码
     * <p>
     * 流的关闭顺序：先打开的后关，后打开的先关，
     * 否则有可能出现java.io.IOException: Stream closed异常
     *
     * @throws IOException
     */
    public static List<String> readFile(String path) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Files.newInputStream(
                        Paths.get(path)), StandardCharsets.UTF_8));
        ) {
            String line = "";
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }
}
