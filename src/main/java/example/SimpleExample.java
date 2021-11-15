package example;

import core.Crawler;

public class SimpleExample {
    public static void main(String[] args) {
        String seed = "https://congregalis.github.io/";
        Crawler.build().addSeed(seed).run();
    }
}
