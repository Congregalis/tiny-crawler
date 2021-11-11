package core.processor.impl;

import core.Page;
import org.jsoup.nodes.Document;
import java.util.HashMap;
import java.util.Map;

public class SimpleTextProcessor extends AbstractProcessor {

    @Override
    public void getResult(Page page) {
        Document document = page.getDocument();
        String title = document.title();
        String text = document.text();
        Map<String, String> result = new HashMap<>();
        result.put("url", page.getUrl());
        result.put("title", title);
        result.put("text", text);
        page.setResults(result);
    }
}