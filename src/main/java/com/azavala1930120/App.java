package com.azavala1930120;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Hello World!");
        // imitacion flujo de datos
        Scanner in = new Scanner(System.in);
        // login
        System.out.println("user");
        String user = in.nextLine();

        System.out.println("pass");
        String pass = in.nextLine();

        System.out.println("recordar");
        int recor = in.nextInt();

        EmailSender es = new EmailSender(user, pass);

        System.out.println("destinatarios");
        in.nextLine();
        String receivers = in.nextLine();

        Map<String, List<String>> data = new HashMap<>();
        if (receivers.contains(".csv")) {
            data = CSVLoader.loadCSV(receivers);
        } else {
            data.put("email", Arrays.asList(receivers.split(",")));
        }

        System.out.println("title");
        String title = in.nextLine();

        System.out.println("text");
        String text = in.nextLine();

        System.out.println("adjuntos");
        String adj = in.nextLine();
        List<String> adjFiles = new ArrayList<>();

        while (!adj.isEmpty()) {
            adjFiles.add(adj);
            adj = in.nextLine();
        }

        List<String> emails = data.get("email");
        int size = data.get("email").size();
        Set<String> columns = data.keySet();

        for (int i = 0; i < size; i++) {
            String newTitle = title, newText = text;
            for (String var : columns) {
                newTitle = newTitle.replace("{" + var + "}", data.get(var).get(i));
                newText = newText.replace("{" + var + "}", data.get(var).get(i));
            }
            es.sendMail(emails.get(i), newTitle, newText, adjFiles);
        }
    }
}
