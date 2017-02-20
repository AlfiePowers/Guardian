package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Ping implements Command {
    private final String HELP = "Usage: !ping";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        event.getTextChannel().sendMessage("Pong!").queue();
        System.out.println("Command, !ping sent!");
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
