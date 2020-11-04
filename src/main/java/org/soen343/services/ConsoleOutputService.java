package org.soen343.services;

import org.soen343.models.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;

public class ConsoleOutputService extends Service {

    private static ConsoleOutputService consoleOutputService = null;
    private Formatter file;
    private String log;
    private String level;

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

    public void errorLog(String message) {
        level = "ERROR";
        log(message);
    }

    private String getTime() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(Model.getSimulationParameters().getDateTime().getDate().getTime());
    }

    private void log(String message) {
        log  = "[" + getTime() + "] " + getLevel() + " " + message;
        notifyObservers(this);
    }

    /*

    public info() {
        return log()
    }
    public critical {


    public error

    public void log (String message, level)
            level + message
    time date

    Model.getSimulationParameters.getDateTime.getDate
    look at simulation parameters
     */


    private void openFile() {
        try {
            file = new Formatter("consoleOutput.txt");
        } catch (Exception exception) {
            System.out.println("The is an error");
        }
    }

    private void writeToFile() {
        file.format("\n", "output file");
    }

    private void closeFile() {
        file.close();
    }

    public String getLevel() {
        return level;
    }
}
