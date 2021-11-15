package core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Filter {
    List<String> rules = new ArrayList<>();

    public void addRule(String rule) {
        rules.add(rule);
    }

    public boolean match(String url) {
        if (rules.size() == 0) return true;

        for (String rule : rules) {
            if (Pattern.matches(rule, url))
                return true;
        }

        return false;
    }

}
