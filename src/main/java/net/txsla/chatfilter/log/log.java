package net.txsla.chatfilter.log;

import net.txsla.chatfilter.config;

public class log {
    public static void add(String log) {
        new Thread (() -> {
            // send log
            if (file.enabled) file.add(log);
            try {
                if (webhook.enabled) webhook.add(log);
            } catch (Exception e) {
                if (config.debug) System.out.println("FAILED TO SEND WEBHOOK!\n"+e);
            }
        }).start();
    }
}
