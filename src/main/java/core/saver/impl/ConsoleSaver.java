package core.saver.impl;

import core.model.Page;
import core.saver.Saver;

import java.util.Map;

public class ConsoleSaver implements Saver {
    @Override
    public void save(Page page) {
        Map<Object, Object> result = page.getResults();
        for (Map.Entry<Object, Object> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("===============================================================");
    }

    @Override
    public void shutdown() {

    }
}
