package com.nibiru.framelib.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 说明：对汉字进行排序按字母A-Z顺序
 *  TODO: 2019/1/15 在使用的时候需要打开65行的注释
 */
public class SortUtil<T> {
    private static SortUtil instance = null;

    public static SortUtil getInstance() {
        if (instance == null) {
            synchronized (SortUtil.class) {
                if (instance == null)
                    instance = new SortUtil();
            }
        }
        return instance;
    }

    public static String getAlphabet(String str) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String pinyin = null;
        try {
            pinyin = (String) PinyinHelper.toHanyuPinyinStringArray(str.charAt(0),
                    defaultFormat)[0];
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pinyin.substring(0, 1);
    }

    /**
     * 按照A到Z的排序,视频排序
     *
     * @param list 排序前的集合
     * @return 排序后的集合
     */
    public List<T> sortATOZ(List<T> list) {
        ArrayList<T> newList = null;
        try {
            newList = new ArrayList();
            if (list == null || list.size() == 0) {
                return null;
            }
            String[] arrays = new String[list.size()];
            for (int i = 0; i < arrays.length; i++) {
                //需要被排序的汉字
                // TODO: 2019/1/15 在使用的时候需要打开这里的注释
//                arrays[i] = ((T) (list.get(i))).getLabel();
                String str = arrays[i];
                if (str.length() == 0) {
                    arrays[i] = arrays[i] + "__" + i;
                    continue;
                }
                String alphabet = str.substring(0, 1);
                if (alphabet.matches("[\\u4e00-\\u9fa5]+")) {
                    str = getAlphabet(str) + "&" + str;
                    arrays[i] = str + "__" + i;
                } else if (alphabet.matches("[a-z]]")) {
                    String s = alphabet.toUpperCase();
                    arrays[i] = s + "&" + arrays[i] + "__" + i;
                } else {
                    arrays[i] = arrays[i] + "__" + i;
                }
            }
             /*设置排序语言环境*/
            Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
            Arrays.sort(arrays, com);  /*遍历数组，去除标识符&及首字母*/
            for (int i = 0; i < arrays.length; i++) {
                String str = arrays[i];
                if (str.contains("&") && str.indexOf("&") == 1) {
                    arrays[i] = str.split("&")[1];
                }
                String[] split = arrays[i].split("__");
                if (split.length > 1) {
                    int befor = Integer.parseInt(split[1]);
                    newList.add(list.get(befor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newList;
    }
}
