package com.yltrcc.novel.utils;

public class TextUtils {

    //智能判断分隔符 使用最靠近的
    private static String separator;

    /**
     * 判断分隔符模式还是最后一位模式 true 表示分隔符 false 最后一位模式
     */
    private static Boolean isActive;

    public static Boolean getIsActive() {
        return isActive;
    }

    public static void setIsActive(Boolean isActive) {
        TextUtils.isActive = isActive;
    }

    private static final String[] separators = {" ", "，", ",", "\"", "“", "\r\n", "\n", ".", "。"};

    //按行读取文本数据，然后按照 分割点进行拆分
    public static String getText(String text) {
        int index = getMaxIndex(text);
        if (index == -1) {
            return text.substring(0);
        }
        if (index + 1 <= text.length() - 1) {
            index += 1;
            return text.substring(index);
        }else {
            index = text.length();
            return text.substring(index);
        }
    }

    /**
     * 读取数据 拼接内容
     * 没有指定分隔符 那就使用默认的分隔符 然后找到最大的index
     *
     * @param text text
     * @return 拼接结果
     */
    public static String joint(String text, String content) {
        return text.substring(0, getMaxIndex(text) + separator.length()) + content;
    }

    private static Integer getMaxIndex(String text) {
        int index = -1;
        int tmpIndex = -1;

        for (String s : separators) {
            //分隔符 “ ”
            tmpIndex = text.lastIndexOf(s);
            //大小判断
            if (tmpIndex > index) {
                index = tmpIndex;
                separator = s;
            }
        }
        return index;
    }
}
