package core.processor.impl;

import core.Page;
import core.processor.Processor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProcessor implements Processor {
    @Override
    public final void process(Page page) {
        setDocument(page);
        getResult(page);
        getNewSeeds(page);
    }

    public void setDocument(Page page) {
        page.setDocument(Jsoup.parse(page.getHtml()));
    };

    public abstract void getResult(Page page);

    public void getNewSeeds(Page page) {
        List<String> links = new ArrayList<>();
        Elements elements = page.getDocument().select("a");
        for (Element element : elements) {
            if (!element.attr("abs:href").equals(""))
                links.add(element.attr("abs:href"));
        }
        page.setNextSeeds(links);
    };
}
