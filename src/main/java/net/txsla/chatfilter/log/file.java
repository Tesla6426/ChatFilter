package net.txsla.chatfilter.log;

import net.txsla.chatfilter.ChatFilter;
import net.txsla.chatfilter.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class file {
    static File logFile;
    static String path;
    static String logFileName;
    public static boolean enabled;
    public static void add(String message) {
        if (!enabled ) return;

        if (config.debug) System.out.println("Adding Log");
        // log text to file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));
            out.write("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + message + "\n");
            out.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public static void loadLogFile() {
        if (!enabled ) return;

        path = System.getProperty("user.dir") + File.separator + ChatFilter.dir + File.separator +  "logs";
        new File(path).mkdirs();

        // file name
        logFileName = "log_" + LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        if (config.debug) System.out.println( path + File.separator + logFileName);
        logFile = new File(path + File.separator + logFileName );

        // load file
        try {
            if (logFile.createNewFile()) {
                if (config.debug) System.out.println("[ProxyChat] [log] new log file created: " + logFileName);
            }else {
                if (config.debug) System.out.println("[ProxyChat] [log] log file loaded: " + logFileName);
            }
        }
        catch (Exception e) {
            System.out.println("[ProxyChat] [log] Failed to load log file!!!");
            e.printStackTrace();
            System.out.println("[ProxyChat] [log] Failed to load log file!!!");
        }
    }
}
