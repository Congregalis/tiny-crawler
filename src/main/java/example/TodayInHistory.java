package example;

import core.Crawler;
import core.model.Page;
import core.processor.impl.AbstractProcessor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 示例：历史上的今天发生了什么？
 * 标签: 单页面爬取 / 自定义处理页面
 * 感谢：https://today.help.bj.cn/ 提供的数据
 */
public class TodayInHistory {

    static class BaikeProcessor extends AbstractProcessor {

        @Override
        public void getResult(Page page) {
            Map<String, String> result = new LinkedHashMap<>();

            Document document = page.getDocument();
            Elements events = document.getElementsByClass("cbp_tmtimeline oh").get(0).getElementsByTag("li");
            for (int i = 1; i < events.size(); i++) {
                Element e = events.get(i);
                String year = e.getElementsByClass("cbp_tmicon").text();
                String desc = e.getElementsByClass("pica").attr("title");

                if (desc.equals("")) desc = e.getElementsByClass("text pr").get(0).getElementsByTag("a").attr("title");

                result.put("event " + i, year + "年，" + desc);
            }
            page.setResults(result);
        }
    }

    public static void main(String[] args) {
        String seed = "https://today.help.bj.cn/";

        Crawler.build().addSeed(seed).setProcessor(new BaikeProcessor()).maxDepth(1).run();
    }
}
