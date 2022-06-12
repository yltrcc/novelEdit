package com.yltrcc.novel.test;


import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        final Trie trie = new Trie();
        trie.add("原始战记第7部分");
        trie.add("第一七二章");
        trie.add("图腾纹不是这个样子嘀");
        trie.add("砰砰砰砰");
        trie.add("石刀交锋的脆响");
        trie.add("在这片游人居住的区域传开");
        trie.add("但周围却没有一个人过来");
        trie.add("都远远站开");
        trie.add("观望着");
        trie.add("游人");
        trie.add("#");
        trie.add("原始战记第8部分");
        trie.add("第二零一章");
        trie.add("你怎么才来");
        trie.add("原始战记第9部分");
        trie.add("第二二八章");
        trie.add("甚是熟悉");
        trie.add("河面上的风向经常变动");
        trie.add("而河里的水向");
        trie.add("很可能也在变动");
        trie.add("这点以前邵玄没注意");
        trie.add("在部落的时候");
        trie.add("只能看到靠近岸边的浅水区");
        trie.add("那里并不明显");
        trie.add("只能看到河水横向的变化");
        trie.add("而出航之后又专注着操控船只");
        trie.add("辨别方位");
        trie.add("也没有注意河水流向变动");
        trie.add("现在细细观察");
        trie.add("才发现");
        trie.add("河流竖向也有变动");
        trie.add("所以有些时候");
        trie.add("船看上去在行驶");
        trie.add("其实可能只是保持着不退而已");
        trie.add("这只是邵玄的猜测");
        trie.add("现在并不能证实");
        trie.add("不过");
        trie.add("邵玄想着");
        trie.add("这条河的其他地方");
        trie.add("是否存在另一条相对稳定的通道");
        trie.add("当初那些前往鹰山的山峰巨鹰");
        trie.add("又是从何处过去的");
        trie.add("为防止遗忘");
        trie.add("邵玄将这条疑惑写在自己的做记录的兽皮卷里");
        trie.add("说不定以后能碰到呢");
        trie.add("尤其是那个");
        trie.add("回");
        trie.add("部落");
        trie.add("部落的船只一直朝着邵玄所指的方向行驶");
        trie.add("一开始有人不适应");
        trie.add("甚至出现晕船的情况");
        trie.add("这点邵玄早有准备");
        trie.add("还和巫研究了晕船的药");
        trie.add("效果有");
        trie.add("只是有限");
        trie.add("窝在船舱里蔫蔫的人不在少数");
        trie.add("而精神抖擞的人");
        trie.add("也被限制行动");
        trie.add("最多只能出来看一看外面的风景");
        trie.add("看看从船下游过去的那些带着斑纹和如刺一般高高背鳍的大鱼们");
        trie.add("河面上的生活很枯燥");
        trie.add("也很紧张");
        trie.add("没有人能睡得安稳");
        trie.add("有时候刚一闭眼");
        trie.add("有时候刚刚一闭眼");
        trie.add("船就晃悠几下");

        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()) {
            String line = sc.nextLine();
            System.out.println(trie.getWordsByPrefix(line));
        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   System.out.println(trie.getWordsByPrefix("b"));

    }
}