package org.soen343.services;

import org.soen343.models.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;

public class ConsoleOutputService extends Service {

    private static ConsoleOutputService consoleOutputService = null;
    private Formatter file;
    private String log;
    private String level;
    private FileWriter fileWriter;

    public static ConsoleOutputService getInstance() {
        if (consoleOutputService == null) {
            consoleOutputService = new ConsoleOutputService();
        }
        return consoleOutputService;
    }

    public String getLog() {
        return log;
    }

    public void infoLog(String message) {
        level = "INFO";
        log(message);
    }

    public void criticalLog(String message) {
        level = "CRITICAL";
        log(message);
    }

    public void warningLog(String message) {
        level = "WARNING";
        log(message);
    }

    private String getTime() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(Model.getSimulationParameters().getDateTime().getDate().getTime());
    }

    private void log(String message) {
        openFile();
        log = "[" + getTime() + "] " + getLevel() + " " + message;
        writeToFile();
        closeFile();
        notifyObservers(this);
    }

    private void openFile() {
        try {
            if (fileWriter==null)
                fileWriter = new FileWriter("consoleOutput.txt");
            else
                fileWriter = new FileWriter("consoleOutput.txt", true);
            file = new Formatter(fileWriter);
        } catch (Exception exception) {
            System.out.println("The is an error");
        }
    }

    private void writeToFile() {
        file.format(log + "\n");
    }

    private void closeFile() {
        try {
            file.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLevel() {
        return level;
    }
}
