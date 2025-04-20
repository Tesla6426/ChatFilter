package net.txsla.chatfilter;

import java.util.List;

public class Filter {
    private String name;
    private boolean enabled;
    private List<String> action;
    private List<String> regex;
    private List<String> commands;

    // constructors
    public Filter(String name) {
        this.name = name;
    }
    public Filter(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
    public Filter(String name, boolean enabled, List<String> action, List<String> regex, List<String> commands) {
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
    public void setRegex(List<String> regex) {
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
    public List<String> getRegex() {
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
