package core.saver.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.model.Page;
import core.saver.Saver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonSaver implements Saver {
    private static final String DEFAULT_NAME = "result.json";

    private final String outputFile;
    private List<String> jsons = new ArrayList<>();

    public JsonSaver() {
        outputFile = DEFAULT_NAME;
        createFile();
    }

    public JsonSaver(String fileName) {
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
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(result);
            jsons.add(jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
            bw.write("[\n" );
            for (int i = 0; i < jsons.size(); i++) {
                bw.write("\t");
                bw.write(jsons.get((i)));
                if (i != jsons.size() - 1) bw.write(",\n");
            }

            bw.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
