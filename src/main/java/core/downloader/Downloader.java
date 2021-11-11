package core.downloader;

import core.Page;

public interface Downloader {

    Page download(String url);
}
