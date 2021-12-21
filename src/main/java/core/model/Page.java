package core.model;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
    // 该页面的种子
    private Seed seed;
    // 原始的 html 文档
    private String html;
    // 解析 html 后的 Jsoup 文档
    private Document document;
    // 该页面下的其他链接，即新种子
    private List<Seed> nextSeeds;
    // 存放处理后的结果，以键值对形式存放比较万金油
    private Map<Object, Object> results;

    public Page() {
        nextSeeds = new ArrayList<>();
        results = new HashMap<>();
    }

    public Page(Seed seed, String html) {
        this.seed = seed;
        this.html = html;
    }

    public String getUrl() {
        return seed.getUrl();
    }

    public Seed getSeed() {
        return seed;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<Seed> getNextSeeds() {
        return nextSeeds;
    }

    public void setNextSeeds(List<Seed> nextSeeds) {
        this.nextSeeds = nextSeeds;
    }

    public Map<Object, Object> getResults() {
        return results;
    }

    public void setResults(Map results) {
        this.results = results;
    }
}
