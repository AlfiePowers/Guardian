package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class ProfileInfo implements Command {
    private final String HELP = "Usage: !profile / !profile [user]";
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Message message = event.getMessage();
        String[] messageArgs = message.toString().split(" ");
        if(messageArgs.length == 1){
            /*
            Nice and simple, this should be for the user themselves that is requesting the profile info
            For reference here, the profile is event.getAuthor() as opposed to the index of 1.
             */
            EmbedBuilder status_builder = new EmbedBuilder();
            // Embed Builder Settings
            status_builder.setTitle(event.getAuthor().getName());
            status_builder.setFooter(event.getAuthor().getName().toString() + " joined Discord on " + event.getAuthor().getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), null);
            status_builder.setThumbnail(event.getAuthor().getAvatarUrl());
            // Content
            status_builder.setDescription("No Description found!");
            status_builder.addField("General Stats:", "Joined " + event.getGuild().getName()+" on: " + event.getGuild().getMember(event.getAuthor())
                    .getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) , true).addField("Name:", "this is not a test", true)
                    .addField("Other Info", "sjfdbnsdaf", true);
            MessageEmbed embed = status_builder.build();
            MessageBuilder mbuilder = new MessageBuilder();

            mbuilder.setEmbed(embed);

            Message message_compiled = mbuilder.build();
            event.getChannel().sendMessage(message_compiled).queue();
        }else{

        }
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
