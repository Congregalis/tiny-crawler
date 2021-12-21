package core.downloader.impl;

import core.model.Page;
import core.downloader.Downloader;
import core.util.HttpUtil;

public class HttpClientDownloader implements Downloader {
    @Override
    public String download(String url) {
        String html = "";
        try {
            html = HttpUtil.getInstance().doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return html;
    }

    public static void main(String[] args) {
        System.out.println(new HttpClientDownloader().download("https://congregalis.github.io/about/"));
    }
}
