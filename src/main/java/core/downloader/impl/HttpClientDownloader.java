package core.downloader.impl;

import core.Page;
import core.downloader.Downloader;
import core.util.HttpUtil;

public class HttpClientDownloader implements Downloader {
    @Override
    public Page download(String url) {
        String html = "";
        try {
            html = HttpUtil.getInstance().doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Page(url, html);
    }

    public static void main(String[] args) {
        System.out.println(new HttpClientDownloader().download("http://localhost:4000/about/").getHtml());
    }
}
