package example;

import core.Crawler;

public class SimpleExample {
    public static void main(String[] args) {
        String seed = "https://baike.baidu.com/item/%E5%BA%95%E7%89%B9%E5%BE%8B/1660180#hotspotmining";
        Crawler.build().addSeed(seed).run();
    }
}
