package com.zss.structures.other;

import com.zss.structures.BaseTest;

import java.util.*;

/**
 * @author ZSS
 * @date 2022/5/14 16:54
 * @desc 找到只有一个字母不同的单词集合
 */
@SuppressWarnings("unused")
public class FindWordInOneDifferTest extends BaseTest {

    /**
     * 该方法计算 89000 个单词需要 75妙
     * 主要在于比较不同长度的单词
     */
    public Map<String, List<String>> computeAdjacentWords(List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<>();

        String[] words = new String[theWords.size()];

        theWords.toArray(words);

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (oneCharOff(words[i], words[j])) {
                    update(adjWords, words[i], words[j]);
                    update(adjWords, words[j], words[i]);
                }
            }
        }
        return adjWords;
    }

    /**
     * 改进型 -- 大致耗时 15秒
     * 计算一个映射，其中键是单词，值是单词列表，这些单词与对应的键只有一个字符不同。
     * 使用二次算法（使用适当的映射），但通过维护一个按单词长度对单词进行分组的附加映射来加快速度；
     */
    public Map<String, List<String>> computeAdjacentWordsV2(List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<>();
        Map<Integer, List<String>> wordsByLength = new TreeMap<>();

        for (String w : theWords) {
            update(wordsByLength, w.length(), w);
        }

        for (List<String> groupsWords : wordsByLength.values()) {
            String[] words = new String[groupsWords.size()];
            groupsWords.toArray(words);
            for (int i = 0; i < words.length; i++) {
                for (int j = 0; j < words.length; j++) {
                    if (oneCharOff(words[i], words[j])) {
                        update(adjWords, words[i], words[j]);
                        update(adjWords, words[j], words[i]);
                    }
                }
            }
        }
        return adjWords;
    }

    /**
     * 该方法有点复杂 -- 计算 89000 个单词在 1秒
     */
    public Map<String, List<String>> computeAdjacentWordsV3(List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<>();
        Map<Integer, List<String>> wordsByLength = new TreeMap<>();

        for (String w : theWords) {
            update(wordsByLength, w.length(), w);
        }

        for (Map.Entry<Integer, List<String>> entry : wordsByLength.entrySet()) {
            List<String> groupsWords = entry.getValue();
            int groupNum = entry.getKey();

            for (int i = 0; i < groupNum; i++) {
                Map<String, List<String>> repToWord = new TreeMap<>();

                for (String str : groupsWords) {
                    String rep = str.substring(0, i) + str.substring(i + 1);
                    update(repToWord, rep, str);
                }

                for (List<String> wordClique : repToWord.values()) {
                    if (wordClique.size() >= 2){
                        for (String s1 : wordClique){
                            for (String s2 : wordClique){
                                if (!Objects.equals(s1, s2)){
                                    update(adjWords, s1, s2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return  adjWords;
    }

    /**
     * 更新map
     */
    private <T> void update(Map<T, List<String>> m, T key, String value) {
        List<String> st = m.computeIfAbsent(key, k -> new ArrayList<>());
        st.add(value);
    }

    /**
     * 如果 word1 和 word2 的长度相同并且只有一个字符不同，则返回 true
     */
    private boolean oneCharOff(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int diffs = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                if (++diffs > 1) {
                    return false;
                }
            }
        }
        return diffs == 1;
    }
}
