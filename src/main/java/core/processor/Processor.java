package core.processor;

import core.model.Filter;
import core.model.Page;

public interface Processor {
    Filter filter = new Filter();

    void process(Page page);

    void addRule(String rule);
}
