package com.thesis.api.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
    public static void write(String entry) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("ThesisLogs.txt", true));
        writer.append(entry);
        writer.append("\n");
        writer.close();
    }
}
