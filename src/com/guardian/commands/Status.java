package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

public class Status implements Command {
    private final String HELP = "Usage: !ping";
    JDA jda;
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();
        long days = TimeUnit.MILLISECONDS.toDays((uptime));
        long hours = TimeUnit.MILLISECONDS.toHours((uptime));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptime);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptime);
        EmbedBuilder status_builder = new EmbedBuilder();
        status_builder.setColor(Color.green);
        status_builder.setTitle("-- Guardian Status --");
        status_builder.setFooter("Guardian is a bot Programmed by Mrporky#0325", null);
        MessageEmbed embed = status_builder.build();
        MessageBuilder mbuilder = new MessageBuilder();
        mbuilder.setEmbed(embed);
        status_builder.addField("Name:", event.getJDA().getSelfUser().getName() + " (ID: " + event.getJDA().getSelfUser().getId() + ")", false);
        status_builder.addField("Uptime:",  days + " days, " +  (hours - (days * 24)) + " hours, " + (minutes - (hours * 60)) + " minutes, " + (seconds - (minutes * 60)) + " seconds", false);
        status_builder.addField("Current Version:", "JDA-3.0.BETA2_128 - Bot Version: 140217v1", false);
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
