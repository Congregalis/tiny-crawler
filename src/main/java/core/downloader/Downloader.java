package core.downloader;

import core.model.Page;

public interface Downloader {

    Page download(String url);
}
