package core.model;

public class Seed {

    String url;
    int depth;

    public Seed(String url) {
        this.url = url;
        this.depth = 1;
    }

    public Seed(String url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Seed{" +
                "url='" + url + '\'' +
                ", depth=" + depth +
                '}';
    }
}
