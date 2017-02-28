package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.List;

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
        EmbedBuilder status_builder = new EmbedBuilder();
        User profile_user = null;
        if (messageArgs.length == 1) {
            profile_user = event.getAuthor();
        } else if (messageArgs.length == 2) {
        }
            /*
            Nice and simple, this should be for the user themselves that is requesting the profile info
            For reference here, the profile is event.getAuthor() as opposed to the index of 1.
             */

        // Embed Builder Settings
        status_builder.setTitle(profile_user.getName());
        status_builder.setFooter(profile_user.getName().toString() + " joined Discord on " + profile_user.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), null);
        status_builder.setThumbnail(profile_user.getAvatarUrl());
        status_builder.setColor(event.getMember().getColor());
        // Content
        if (event.getMember().getOnlineStatus().toString() == "ONLINE") {
            status_builder.setDescription("Currently online");
        } else if (event.getMember().getOnlineStatus().toString() == "IDLE") {
            status_builder.setDescription("Currently Idling");
        } else if (event.getMember().getOnlineStatus().toString() == "DO_NOT_DISTURB") {
            status_builder.setDescription("Do not disturb");
        } else if (event.getMember().getOnlineStatus().toString() == "OFFLINE") {
            status_builder.setDescription("Currently Offline");
        }
        List<Role> role = event.getGuild().getRoles();
        List roles = event.getMember().getRoles();
        LinkedList userRole = new LinkedList();
        String roles_out;
        for (int i = 0; i < role.size(); i++) {
            if (roles.contains(role.get(i))) {
                userRole.add(userRole.size(), role.get(i).getName());
            }
        }
        roles_out = userRole.toString().replace("[", "").replace("]", "").replaceAll(", ", "\n");
        status_builder.addField("General Stats:", "Joined " + event.getGuild().getName() + "\n on: " + event.getGuild().getMember(event.getAuthor())
                .getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME), true).addField("Roles:", roles_out.toString(), true)
                .addField("Other Info", "", true);

        // Build the Message
        MessageEmbed embed = status_builder.build();
        MessageBuilder mbuilder = new MessageBuilder();
        mbuilder.setEmbed(embed);
        Message message_compiled = mbuilder.build();

        // Output the message
        event.getChannel().sendMessage(message_compiled).queue();
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
