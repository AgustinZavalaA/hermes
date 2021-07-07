package com.azavala1930120;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVLoader {
    static Map<String, List<String>> loadCSV(String filepath) throws IOException, FileNotFoundException {
        Map<String, List<String>> csv = new HashMap<>();
        BufferedReader fileBuffer = new BufferedReader(new FileReader(filepath));
        String line;

        // get columns name
        String varTitles[] = fileBuffer.readLine().replaceAll("[ \\t]", "").split(",");
        for (String t : varTitles)
            csv.put(t, new ArrayList<String>());

        while ((line = fileBuffer.readLine()) != null) {
            String vars[] = line.split(",");
            for (int i = 0; i < vars.length; i++) {
                List<String> list = csv.get(varTitles[i]);
                list.add(vars[i]);
            }
        }
        fileBuffer.close();

        return csv;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String f = "/mnt/d/Programacion/Java/hermes/src/main/java/com/azavala1930120/data/xd.csv";
        Map<String, List<String>> xd = CSVLoader.loadCSV(f);

        System.out.println(xd.containsKey("email"));

        for (String key : xd.keySet())
            System.out.println(key + " : " + xd.get(key));
    }
}
