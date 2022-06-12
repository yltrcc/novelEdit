package com.yltrcc.novel;

import java.util.*;

public class AlgorithmTest {

    static List<String> ans = new ArrayList<String>();
    static int[] segments = new int[4];

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("s", 1);
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.println(entry.getKey());
        }

        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return 0;
            }
        });
    }










}
