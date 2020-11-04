package org.soen343.services;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Formatter;
import javafx.application.Application;

public class ConsoleOutputService extends Service{

    private Formatter file;
    private String log;
    private static ConsoleOutputService consoleOutputService = null;

    public static ConsoleOutputService getInstance() {
        if (consoleOutputService == null) {
            consoleOutputService = new ConsoleOutputService();
        }
        return consoleOutputService;
    }

    public String getLog() {
        return log;
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
    public void log(String message) {
        log = message;
        notifyObservers(this);
    }

    public void openFile(){
        try {
            file = new Formatter("consoleOutput.txt");
        }
        catch (Exception exception) {
            System.out.println("The is an error");
        }
    }

    public void writeToFile() {
        file.format("\n", "output file");
    }

    public void closeFile(){
        file.close();
    }
}
