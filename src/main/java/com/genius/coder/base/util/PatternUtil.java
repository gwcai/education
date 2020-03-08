package com.genius.coder.base.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
public final class PatternUtil {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("([[a-z0-9A-Z]+_?]+[a-z0-9A-Z]+@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14})");
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(^\\d[^0]\\d{1,2}[-]?\\d{7,8}|13\\d{9}|14\\d{9}|15\\d{9}|17\\d{9}|18\\d{9})");
    public static final Pattern VOXER_PATTERN = Pattern.compile("(([vV][oO][xX][eE][rR][a-zA-Z\\u4e00-\\u9fa5]*\\s*[:：]*\\s*)([a-zA-Z0-9]+))");
    public static final Pattern WECHAT_PATTERN = Pattern.compile("(([微][信][a-zA-Z\\u4e00-\\u9fa5]*\\s*[:：]*\\s*)([a-zA-Z0-9]+))");
    public static final Pattern BANK_PATTERN = Pattern.compile("(([银][行][a-zA-Z\\u4e00-\\u9fa5]*\\s*[:：]*\\s*)([a-zA-Z0-9\\s]+))");
    public static final Pattern QQ_PATTERN = Pattern.compile("(([qQ][qQ][a-zA-Z\\u4e00-\\u9fa5]*\\s*[:：]*\\s*)([1-9][0-9]+))");
    public static final Pattern IDCARD_PATTERN = Pattern.compile("(\\d{17}[0-9a-zA-Z])");
    public static final Pattern NUMBER_PATTERN = Pattern.compile("([\\d]{6,})");

    public static Set<String> getNumbers(String content, int d) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = NUMBER_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group();
                result.add(temp);
            }

            return (Set)result.parallelStream().filter((r) -> {
                return d == r.length();
            }).collect(Collectors.toSet());
        }
    }

    public static Set<String> getBankNumbers(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = BANK_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group(3);
                result.add(temp);
            }

            return result;
        }
    }

    public static Set<String> getIdCards(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = IDCARD_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group();
                result.add(temp);
            }

            return result;
        }
    }

    public static Set<String> getQQ(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = QQ_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group(3);
                result.add(temp);
            }

            return result;
        }
    }

    public static Set<String> getWeChat(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = WECHAT_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group(3);
                result.add(temp);
            }

            return result;
        }
    }

    public static Set<String> getVoxers(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = VOXER_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group(3);
                result.add(temp);
            }

            return result;
        }
    }

    public static Set<String> getEmails(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = EMAIL_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group();
                result.add(temp);
            }

            return result;
        }
    }

    public static Set<String> getPhoneNumbers(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        } else {
            Set<String> result = new HashSet();
            Matcher m = PHONE_NUMBER_PATTERN.matcher(content);

            while(m.find()) {
                String temp = m.group();
                result.add(temp);
            }

            return result;
        }
    }

    private PatternUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}