package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

public class BannedCommand implements Command {
    private final String HELP = "Usage: !banned";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("").queue();
        EmbedBuilder status_builder = new EmbedBuilder();
        status_builder.setColor(event.getMember().getColor());
        MessageEmbed embed = status_builder.build();
        MessageBuilder mbuilder = new MessageBuilder();
        mbuilder.setEmbed(embed);
        status_builder.setImage("http://i.imgur.com/rXsUtuJ.gif");
        Message message = mbuilder.build();
        event.getChannel().sendMessage(message).queue();
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
