package core.processor.impl;

import core.model.Page;
import core.processor.Processor;
import core.util.Md5Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractProcessor implements Processor {

    // 用于去重
    private Set<String> linkSet = new HashSet<>();

    @Override
    public final void process(Page page) {
        setDocument(page);
        getResult(page);
        getNewSeeds(page);
    }

    public void setDocument(Page page) {
        page.setDocument(Jsoup.parse(page.getHtml(), page.getUrl()));
    };

    public abstract void getResult(Page page);

    public void getNewSeeds(Page page) {
        List<String> links = new ArrayList<>();
        Elements elements = page.getDocument().select("a");
        for (Element element : elements) {
            String nextUrl = !element.baseUri().equals("") ? element.attr("abs:href") : element.attr("href");
            if (!nextUrl.equals("") && linkSet.add(Md5Util.getInstance().md5(nextUrl))) links.add(nextUrl);
        }
        page.setNextSeeds(links);
    }
}
