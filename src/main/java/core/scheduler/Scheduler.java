package core.scheduler;

import core.model.Seed;

public interface Scheduler {

    void offer(Seed seed);

    Seed poll();
}
