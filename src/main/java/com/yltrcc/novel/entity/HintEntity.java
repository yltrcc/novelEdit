package com.yltrcc.novel.entity;

public class HintEntity {

    private String originalStr;

    private String replaceStr;

    public String getOriginalStr() {
        return originalStr;
    }

    public void setOriginalStr(String originalStr) {
        this.originalStr = originalStr;
    }

    public String getReplaceStr() {
        return replaceStr;
    }

    public void setReplaceStr(String replaceStr) {
        this.replaceStr = replaceStr;
    }

    @Override
    public String toString() {
        return replaceStr;
    }
}
