package com.tujia.myssm.solution.leetcode;

import java.util.List;

/**
 *
 * @author: songlinl
 * @create: 2023/7/2 13:13
 */
public class T648 {

    public String replaceWords(List<String> dictionary, String sentence) {

        dictionary.sort((a, b) -> a.length() - b.length());

        String[] wordArr = sentence.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < wordArr.length; i++) {
            String res = replace(wordArr[i], dictionary);
            if (i == 0) {
                builder.append(res);
            } else {
                builder.append(" " + res);
            }
        }
        return builder.toString();

    }

    private String replace(String word, List<String> dictionary) {
        for (String d : dictionary) {
            if (word.startsWith(d)) {
                return d;
            }
        }
        return word;
    }
}
