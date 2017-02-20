package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class QueueInfo implements Command {
    private final String HELP = "Usage: !ping";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder status_builder = new EmbedBuilder();
        status_builder.setColor(Color.green);
        status_builder.setFooter("" , null);
        status_builder.setTitle("Songs currently requested for " + event.getGuild().getName());
        MessageEmbed embed = status_builder.build();
        MessageBuilder mbuilder = new MessageBuilder();
        mbuilder.setEmbed(embed);
        if(TrackScheduler.queue == null){
            status_builder.addField("Queue Info:", "The queue is currently empty! Type, !play [SongURL] to request a song!", false);
        }else if(TrackScheduler.queue.size() == 0){
            status_builder.addField("Queue Info:", "There is currently " + (TrackScheduler.queue.size() + 1) + " song in the queue", false);

        }else {
            status_builder.addField("Queue Info:", "There are currently " + (TrackScheduler.queue.size() + 1) + " songs in the queue", false);
        }
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
