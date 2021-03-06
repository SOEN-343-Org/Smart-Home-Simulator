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

    /**
     * Get instance
     *
     * @return consoleOuputService
     */
    public static ConsoleOutputService getInstance() {
        if (consoleOutputService == null) {
            consoleOutputService = new ConsoleOutputService();
        }
        return consoleOutputService;
    }

    /**
     * Get log
     *
     * @return log
     */
    public String getLog() {
        return log;
    }

    /**
     * Setting infor log level
     *
     * @param message
     */
    public void infoLog(String message) {
        level = "INFO";
        log(message);
    }

    /**
     * Setting critical log level
     *
     * @param message
     */
    public void criticalLog(String message) {
        level = "CRITICAL";
        log(message);
    }

    /**
     * Setting error log level
     *
     * @param message
     */
    public void warningLog(String message) {
        level = "WARNING";
        log(message);
    }

    /**
     * Get time
     *
     * @return time
     */
    private String getTime() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(Model.getSimulationParameters().getDateTime().getDate().getTime());
    }

    /**
     * Log message
     *
     * @param message
     */
    private void log(String message) {
        openFile();
        log = "[" + getTime() + "] " + getLevel() + " " + message;
        writeToFile();
        closeFile();
        notifyObservers(this);
    }

    private void openFile() {
        try {
            fileWriter = new FileWriter("consoleOutput.txt", fileWriter != null);
            file = new Formatter(fileWriter);
        } catch (Exception exception) {
            System.out.println("Error");
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

    /**
     * Get level
     *
     * @return level
     */
    public String getLevel() {
        return level;
    }
}
