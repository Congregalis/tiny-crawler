package core.scheduler;

public interface Scheduler {

    void offer(String url);

    String poll();
}
