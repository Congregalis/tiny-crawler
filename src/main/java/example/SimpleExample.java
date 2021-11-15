package example;

import core.Crawler;

public class SimpleExample {
    public static void main(String[] args) {
        // 种子url
        String seed = "https://congregalis.github.io/";
        // 匹配任何“年月日”形式的日期，连接符可以没有或是 . / - 之一
        String dateRegex = "(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))";

        Crawler.build().addSeed(seed).addRule("https://congregalis.github.io/" + dateRegex + "/.*/").run();
    }
}
