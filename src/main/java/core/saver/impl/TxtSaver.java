package core.saver.impl;

import core.model.Page;
import core.saver.Saver;

import java.io.*;
import java.util.Map;

public class TxtSaver implements Saver {
    private static final String DEFAULT_NAME = "result.txt";

    private final String outputFile;

    public TxtSaver() {
        outputFile = DEFAULT_NAME;
        createFile();
    }

    public TxtSaver(String fileName) {
        outputFile = fileName;
        createFile();
    }

    public void createFile() {
        File file = new File(outputFile);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Page page) {
        Map<Object, Object> result = page.getResults();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
            for (Map.Entry<Object, Object> entry : result.entrySet()) {
                bw.write(entry.getKey() + " : " + entry.getValue());
                bw.write("\n");
            }
            bw.write("===============================================================");
            bw.write("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
