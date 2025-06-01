package net.txsla.chatfilter;

import java.util.List;
import java.util.regex.Pattern;

public class Filter {
    private String name;
    private boolean enabled;
    private List<String> action;
    private List<Pattern> regex;
    private List<String> commands;

    // constructors
    public Filter(String name) {
        this.name = name;
    }
    public Filter(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
    public Filter(String name, boolean enabled, List<String> action, List<Pattern> regex, List<String> commands) {
        this.name = name;
        this.enabled = enabled;
        this.action = action;
        this.regex = regex;
        this.commands = commands;
    }

    // set
    public void setActions(List<String> actions) {
        this.action = actions;
    }
    public void setRegex(List<Pattern> regex) {
        this.regex = regex;
    }
    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    // get
    public String getName() {
        return this.name;
    }
    public List<String> getAction() {
        return action;
    }
    public List<Pattern> getRegex() {
        return this.regex;
    }
    public List<String> getCommands() {
        return this.commands;
    }

    // enable(d)
    public boolean isEnabled() {
        return enabled;
    }
    public void enable() {
        enabled = true;
    }
    public void disable() {
        enabled = false;
    }

}
