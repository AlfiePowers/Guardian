package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BannedCommand implements Command {
    private final String HELP = "Usage: !banned";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
